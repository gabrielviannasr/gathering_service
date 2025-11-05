package br.com.gathering.service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.gathering.entity.Event;
import br.com.gathering.entity.Player;
import br.com.gathering.entity.Result;
import br.com.gathering.projection.ConfraPotProjection;
import br.com.gathering.projection.LoserPotProjection;
import br.com.gathering.projection.RankCountProjection;
import br.com.gathering.projection.RankProjection;
import br.com.gathering.repository.EventRepository;

@Service
public class EventService extends AbstractService<Event> {

	// The worst-ranked player takes the largest piece of loserPot
	private static final double WORST_RANK_LOSER_POT_PERCENTAGE = 0.6;
	// The second worst-ranked players share the smallest piece of loserPot
	private static final double SECOND_WORST_RANK_LOSER_POT_PERCENTAGE = 0.4;

	@Autowired
	private EventRepository repository;

	public static Sort getSort() {
		return Sort.by(Order.asc("idGathering"), Order.asc("createdAt"));
	}

	public List<Event> getList(Event model) {
		return repository.findAll(getExample(model), getSort());
	}

	public Page<Event> getPage(Event model, Sort sort, int page, int size) {
		return repository.findAll(getExample(model), PageRequest.of(page, size, sort));
	}

	public Event getById(Long id) {
		Optional<Event> optional = repository.findById(id);
		return optional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	public Event save(Event model) {
		model.init();
		validate(model);
		return repository.save(model);
	}
	
	private void validate(Event model) {
		
		for (int players = 4; players <= 6; players++) {
		    Double total = players * model.getRoundFee();
		    Double loserFee = switch (players) {
		        case 4 -> model.getLoserFee4();
		        case 5 -> model.getLoserFee5();
		        case 6 -> model.getLoserFee6();
		        default -> 0.0;
		    };
		    if (loserFee > total) {
		        throw new ResponseStatusException(
		            HttpStatus.BAD_REQUEST,
		            String.format("LoserFee%d (%.2f) exceeds total amount available for a %d-player round (%.2f)",
		                players, loserFee, players, total)
		        );
		    }
		}
	}
	
	public ConfraPotProjection getConfraPot(Long idEvent) {
		ConfraPotProjection confraPot = repository.getConfraPot(idEvent);

		// Log
		System.out.printf(
			"ConfraPot: { players: %-2d | confraPot: %.2f }%n",
			confraPot.getPlayers(),
			confraPot.getConfraPot()
        );

		return confraPot;
	}

	public LoserPotProjection getLoserPot(Long idEvent) {
		LoserPotProjection loserPot = repository.getLoserPot(idEvent);

		// Log
		System.out.printf(
			"LoserPot: { rounds: %-2d | loserPot: %.2f }%n",
			loserPot.getRounds(),
			loserPot.getLoserPot()
        );

		return loserPot;
	}
	
	public List<RankCountProjection> getRankCount(Long idEvent) {
		List<RankCountProjection> list = repository.getRankCount(idEvent);

		// Log
		list.forEach(item -> 
			System.out.printf(
	            "\t{ rank: %-2d | count: %-2d }%n",
	            item.getRank(),
	            item.getCount()
	        )
		);

		return list;
	}

	public List<RankProjection> getRankProjection(Long idEvent) {
		List<RankProjection> list = repository.getRankProjection(idEvent);

		int maxNameLength = list.stream()
		    .map(RankProjection::getPlayerName)
		    .filter(Objects::nonNull)
		    .mapToInt(String::length)
		    .max()
		    .orElse(25); // fallback

//		String format = "\t{ rank: %-2d | name: %-" + Player.NAME_LENGTH + "s | rankBalance: %8.2f }%n";
		String format = "\t{ rank: %-2d | name: %-" + maxNameLength + "s | rankBalance: %8.2f }%n";
		
		// Log
		list.forEach(item -> 
	        System.out.printf(
	            format,
	            item.getRank(),
	            item.getPlayerName(),
	            item.getRankBalance()
	        )
	    );

		return list;
	}
	
	public List<Result> getResult(Long idEvent) {
	    // Rank data without loser pot distribution
	    List<RankProjection> ranks = repository.getRankProjection(idEvent);

	    if (ranks == null || ranks.isEmpty()) {
	        System.out.printf("No ranks found for event ID %d%n", idEvent);
	        return Collections.emptyList();
	    }

	    // Cast rank projection to result
	    List<Result> results = ranks.stream()
	        .map(p -> Result.builder()
	            .idEvent(idEvent)
	            .idPlayer(p.getIdPlayer())
	            .playerName(p.getPlayerName())
	            .rank(p.getRank())
	            .wins(p.getWins())
	            .rounds(p.getRounds())
	            .positive(p.getPositive())
	            .negative(p.getNegative())
	            .rankBalance(p.getRankBalance())
	            .loserPot(0.0)
	            .finalBalance(p.getRankBalance()) // inicial igual
	            .build())
	        .collect(Collectors.toList());

	    // Helper data
	    LoserPotProjection loserPot = repository.getLoserPot(idEvent);
	    List<RankCountProjection> rankCount = repository.getRankCount(idEvent);

	    if (loserPot == null || rankCount == null || rankCount.isEmpty()) {
	        System.err.printf("Missing data for loser pot distribution in event %d%n", idEvent);
	        return results; // Return result without loser pot (useful yet)
	    }

	    // Distribute loserPot based on the rankCount 
	    if (rankCount.get(0).getCount() > 1) {
	        distributeLoserPotEqually(idEvent, results, loserPot.getLoserPot(), rankCount);
	    } else {
	        distributeLoserPotUnequally(idEvent, results, loserPot.getLoserPot(), rankCount);
	    }

		int maxNameLength = results.stream()
		    .map(Result::getPlayerName)
		    .filter(Objects::nonNull)
		    .mapToInt(String::length)
		    .max()
		    .orElse(25); // fallback
	    
	    // Log
	    results.forEach(item -> 
	        System.out.printf(
//        		"\t{ rank: %-2d | name: %-25s | rankBalance: %8.2f | loserPot: %8.2f | finalBalance: %8.2f }%n",
//	            "\t{ rank: %-2d | name: %-" + Player.NAME_LENGTH + "s | rankBalance: %8.2f | loserPot: %8.2f | finalBalance: %8.2f }%n",
	            "\t{ rank: %-2d | name: %-" + maxNameLength + "s | rankBalance: %8.2f | loserPot: %8.2f | finalBalance: %8.2f }%n",
	            item.getRank(),
	            item.getPlayerName(),
	            item.getRankBalance(),
	            item.getLoserPot(),
	            item.getFinalBalance()
	        )
	    );

	    return results;
	}

	// Helper method to distribute loserPot equally
	private void distributeLoserPotEqually(Long idEvent, List<Result> results, Double loserPot, List<RankCountProjection> rankCount) {
	    // LoserPot equally divided among the worst-ranked players
	    Double percentage = 1.0 / rankCount.get(0).getCount();

	    // Loop to update loserPot and finalBalance
	    results.forEach(item -> {
	        // Non-worst-ranked players take 0% of loserPot
	        Double pot = 0.0;

	        // If player rank is the (1st) worst rank, update finalBalance
	        if (item.getRank() == rankCount.get(0).getRank()) {
	            pot = percentage * loserPot;
	            item.setFinalBalance(item.getFinalBalance() + pot);
	        }

	        // Update loserPot
	        item.setLoserPot(pot);

	        // Update idEvent
	        item.setIdEvent(idEvent);
	    });
	}

	// Helper method to distribute loserPot unequally
	private void distributeLoserPotUnequally(Long idEvent, List<Result> results, Double loserPot, List<RankCountProjection> rankCount) {
		// Smallest piece of loserPot equally divided among the 2nd worst-ranked players
	    Double percentage = SECOND_WORST_RANK_LOSER_POT_PERCENTAGE / rankCount.get(1).getCount();

	    // Loop to update loserPot and finalBalance
	    results.forEach(item -> {
	        // Non-worst-ranked players take 0% of loserPot
	        Double pot = 0.0;

	        // If player rank is the (1st) worst rank, update finalBalance
	        if (item.getRank() == rankCount.get(0).getRank()) {
	            pot = WORST_RANK_LOSER_POT_PERCENTAGE * loserPot;
	            item.setFinalBalance(item.getFinalBalance() + pot);
	        }
	        // If player rank is the 2nd worst rank, update finalBalance
	        else if (item.getRank() == rankCount.get(1).getRank()) {
	            pot = percentage * loserPot;
	            item.setFinalBalance(item.getFinalBalance() + pot);
	        }

	        // Update loserPot
	        item.setLoserPot(pot);

	        // Update idEvent
	        item.setIdEvent(idEvent);
	    });
	}

}
