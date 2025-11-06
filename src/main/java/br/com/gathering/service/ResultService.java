package br.com.gathering.service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.gathering.entity.Event;
import br.com.gathering.entity.Result;
import br.com.gathering.entity.Transaction;
import br.com.gathering.factory.TransactionFactory;
import br.com.gathering.projection.ConfraPotProjection;
import br.com.gathering.projection.LoserPotProjection;
import br.com.gathering.projection.RankCountProjection;
import br.com.gathering.projection.RankProjection;
import br.com.gathering.repository.EventRepository;
import br.com.gathering.repository.ResultRepository;
import br.com.gathering.repository.TransactionRepository;

@Service
public class ResultService extends AbstractService<Result> {

	private static final Logger log = LoggerFactory.getLogger(ResultService.class);

	// The worst-ranked player takes the largest piece of loserPot
	private static final double WORST_RANK_LOSER_POT_PERCENTAGE = 0.6;
	// The second worst-ranked players share the smallest piece of loserPot
	private static final double SECOND_WORST_RANK_LOSER_POT_PERCENTAGE = 0.4;

	@Autowired
	private ResultRepository repository;

	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private TransactionRepository transactionRepository;

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

    /**
     * Calculates and persists the event results.
     * The process is transactional — if any error occurs,
     * no partial result is committed.
     */
	@Transactional(rollbackFor = Exception.class)
	public List<Result> saveResult(Long idEvent) {
		log.info("Starting result calculation for event {}", idEvent);

		try {
            // 1. Remove previous transactions to avoid duplicates
//            transactionRepository.deleteByIdEvent(idEvent);
            int oldCount = transactionRepository.findByIdEvent(idEvent).size();
            if (oldCount > 0) {
            	transactionRepository.deleteByIdEvent(idEvent);
                log.info("Deleted {} previous transactions for event {}", oldCount, idEvent);
            }

			// 2. Remove previous results to avoid duplicates
//			repository.deleteByIdEvent(idEvent);
            oldCount = repository.findByIdEvent(idEvent).size();
            if (oldCount > 0) {
                repository.deleteByIdEvent(idEvent);
                log.info("Deleted {} previous results for event {}", oldCount, idEvent);
            }

		    // 3. Compute new results
		    List<Result> results = getResult(idEvent);
		    if (results == null || results.isEmpty()) {
//		    	System.out.printf("No results generated for event %d%n", idEvent);
                log.warn("No results generated for event {}", idEvent);
                return Collections.emptyList();
            }
	
		    // 4. Persist data
		    List<Result> savedResults = repository.saveAll(results);
		    log.info("{} results saved successfully for event {}", savedResults.size(), idEvent);	
//		    System.out.printf("%d results saved for event %d%n", savedResults.size(), idEvent);

		    // 5. Log summary
            int maxNameLength = savedResults.stream()
                .map(Result::getPlayerName)
                .filter(Objects::nonNull)
                .mapToInt(String::length)
                .max()
                .orElse(25);

            savedResults.forEach(item ->
//                log.info(String.format(
//            		"{ rank: %-2d | name: %-" + maxNameLength + "s | rankBalance: %8.2f | loserPot: %8.2f | finalBalance: %8.2f }",
            	System.out.printf(String.format(
                    "\t{ rank: %-2d | name: %-" + maxNameLength + "s | rankBalance: %8.2f | loserPot: %8.2f | finalBalance: %8.2f }%n",
                    item.getRank(),
                    item.getPlayerName(),
                    item.getRankBalance(),
                    item.getLoserPot(),
                    item.getFinalBalance()
                ))
            );

            // 6. Create and save transactions (via factory)
//            Event event = eventRepository.findById(idEvent).get();
            Event event = eventRepository.findById(idEvent)
                    .orElseThrow(() -> new IllegalArgumentException("Event not found for id " + idEvent));
            
//            LocalDateTime createdAt = event.getCreatedAt();
//            LocalDateTime createdAt = LocalDateTime.now();

//            List<Transaction> transactions = results.stream()
//                .flatMap(result -> Stream.of(
//                    Transaction.builder()
//                    	.idGathering(event.getIdGathering())
//                        .idEvent(idEvent)
//                        .idPlayer(result.getIdPlayer())
//                        .idTransactionType(TransactionType.INSCRICAO.getId())
//                        .createdAt(createdAt)
//                        .amount(-event.getConfraFee())
//                        .description(TransactionType.INSCRICAO.getDescription())
//                        .build(),
//                    Transaction.builder()
//                    	.idGathering(event.getIdGathering())
//                        .idEvent(idEvent)
//                        .idPlayer(result.getIdPlayer())
//                        .idTransactionType(TransactionType.RESULTADO.getId())
//                        .createdAt(createdAt)
//                        .amount(result.getFinalBalance())
//                        .description(TransactionType.RESULTADO.getDescription())
//                        .build()
//                ))
//                .collect(Collectors.toList());
            
            List<Transaction> transactions = TransactionFactory.fromResults(event, results);
            transactionRepository.saveAll(transactions);
	
		    return savedResults;

		} catch (Exception ex) {
            log.error("Error while calculating/saving results for event {}: {}", idEvent, ex.getMessage(), ex);
            throw ex; // Auto rollback by Spring
        }
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
