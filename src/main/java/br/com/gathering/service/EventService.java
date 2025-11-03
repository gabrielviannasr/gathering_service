package br.com.gathering.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.gathering.entity.Event;
import br.com.gathering.entity.Rank;
import br.com.gathering.projection.ConfraPotProjection;
import br.com.gathering.projection.LoserPotProjection;
import br.com.gathering.projection.PlayerRoundProjection;
import br.com.gathering.projection.PotProjection;
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

	public PotProjection getPot(Long idEvent) {
		PotProjection pot = repository.getPot(idEvent);
		if (pot == null) {
			return null;
		}
		System.out.println("Pot: { confraPot: " + pot.getConfraPot() + ", loserPot: " + pot.getLoserPot() + " }");
		return pot;
	}

	public PlayerRoundProjection getPlayerRound(Long idEvent) {
		PlayerRoundProjection playerRound = repository.getPlayerRound(idEvent);
		System.out.println("PlayerRound: { players: " + playerRound.getPlayers() + ", rounds: " + playerRound.getRounds() + " }");
		return playerRound;
	}
	
	public ConfraPotProjection getConfraPot(Long idEvent) {
		ConfraPotProjection confraPot = repository.getConfraPot(idEvent);
		System.out.println(String.format("ConfraPot: { players: %d, confraPot: %.2f }", confraPot.getPlayers(), confraPot.getConfraPot()));
		return confraPot;
	}

	public LoserPotProjection getLoserPot(Long idEvent) {
		LoserPotProjection loserPot = repository.getLoserPot(idEvent);
		System.out.println(String.format("LoserPot: { rounds: %d, loserPot: %.2f }", loserPot.getRounds(), loserPot.getLoserPot()));
		return loserPot;
	}
	
	public List<RankCountProjection> getRankCount(Long idEvent) {
		List<RankCountProjection> list = repository.getRankCount(idEvent);
		System.out.println("RankCount: [");
		list.forEach(item -> {
			System.out.println("\t{ rank: " + item.getRank() + ", count: " + item.getCount() + " },");
		});
		System.out.println("]");
		return list;
	}

	public List<RankProjection> getRankProjection(Long idEvent) {
		List<RankProjection> list = repository.getRankProjection(idEvent);
		System.out.println("Rank: [");
		list.forEach(item -> {			
			System.out.println(String.format("{ rank: %d, name: %s, rankBalance: %.2f }", item.getRank(), item.getPlayerName(), item.getRankBalance()));
		});
		System.out.println("]");
		return list;
	}

	/**
	 * Calculate and update the final balance of players based on the distribution of loserPot.
	 * 
	 * If there are two or more worst-ranked players, they share equally the loserPot.
	 * 
	 * Else the worst-ranked player takes the largest piece of loserPot, and the 2nd worst-ranked players share equally the smallest piece of loserPot.
	 *
	 * @param idEvent The ID of the event.
	 * @return The updated list of ranks.
	 */
	public Event getRank(Long idEvent) {
		// Get loserPot and confraPot
		PotProjection pot = repository.getPot(idEvent);
		
		if (pot == null) {
			return null;
		}

		// Get rank count to determine how many players will share the loserPot
		List<RankCountProjection> rankCount = getRankCount(idEvent);

		// Get rank list to update the loserPot and finalBalance
		List<Rank> ranks = repository.getRank(idEvent);

		// Distribute loserPot based on the rankCount
	    if (rankCount.get(0).getCount() > 1) {
	        distributeLoserPotEqually(idEvent, ranks, pot, rankCount);
	    } else {
	        distributeLoserPotUnequally(idEvent, ranks, pot, rankCount);
	    }

		// Get players and rounds total
		PlayerRoundProjection playerRound = getPlayerRound(idEvent);

		/* Update event */
		Event event = getById(idEvent);		
		event.setPlayers(playerRound.getPlayers());
		event.setRounds(playerRound.getRounds());
		event.setConfraPot(pot.getConfraPot());
		event.setLoserPot(pot.getLoserPot());

		// Synchronize the ranks collection with the current state of the list
//		event.getRanks().clear();
//		event.getRanks().addAll(ranks);

		event = save(event);
		System.out.println(event);
		return event;
	}

	// Helper method to distribute loserPot equally
	private void distributeLoserPotEqually(Long idEvent, List<Rank> ranks, PotProjection pot, List<RankCountProjection> rankCount) {
	    // LoserPot equally divided among the worst-ranked players
	    Double percentage = 1.0 / rankCount.get(0).getCount();

	    // Loop to update loserPot and finalBalance
	    ranks.forEach(item -> {
	        // Non-worst-ranked players take 0% of loserPot
	        Double loserPot = 0.0;

	        // If player rank is the (1st) worst rank, update finalBalance
	        if (item.getRank() == rankCount.get(0).getRank()) {
	            loserPot = percentage * pot.getLoserPot();
	            item.setFinalBalance(item.getFinalBalance() + loserPot);
	        }

	        // Update loserPot
	        item.setLoserPot(loserPot);

	        // Update idEvent
	        item.setIdEvent(idEvent);
	    });
	}

	// Helper method to distribute loserPot unequally
	private void distributeLoserPotUnequally(Long idEvent, List<Rank> ranks, PotProjection pot, List<RankCountProjection> rankCount) {
		// Smallest piece of loserPot equally divided among the 2nd worst-ranked players
	    Double percentage = SECOND_WORST_RANK_LOSER_POT_PERCENTAGE / rankCount.get(1).getCount();

	    // Loop to update loserPot and finalBalance
	    ranks.forEach(item -> {
	        // Non-worst-ranked players take 0% of loserPot
	        Double loserPot = 0.0;

	        // If player rank is the (1st) worst rank, update finalBalance
	        if (item.getRank() == rankCount.get(0).getRank()) {
	            loserPot = WORST_RANK_LOSER_POT_PERCENTAGE * pot.getLoserPot();
	            item.setFinalBalance(item.getFinalBalance() + loserPot);
	        }
	        // If player rank is the 2nd worst rank, update finalBalance
	        else if (item.getRank() == rankCount.get(1).getRank()) {
	            loserPot = percentage * pot.getLoserPot();
	            item.setFinalBalance(item.getFinalBalance() + loserPot);
	        }

	        // Update loserPot
	        item.setLoserPot(loserPot);

	        // Update idEvent
	        item.setIdEvent(idEvent);
	    });
	}

}
