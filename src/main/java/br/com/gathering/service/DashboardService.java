package br.com.gathering.service;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.gathering.projection.RankProjection;
import br.com.gathering.projection.gathering.FormatProjection;
import br.com.gathering.projection.gathering.PlayerTransactionProjection;
import br.com.gathering.projection.gathering.PlayerWalletProjection;
import br.com.gathering.projection.gathering.GatheringSummaryProjection;
import br.com.gathering.repository.DashboardRepository;

@Transactional(readOnly = true)
@Service
public class DashboardService {

	private static final Logger log = LoggerFactory.getLogger(ResultService.class);
	
	@Autowired
	private DashboardRepository repository;
	
	public List<PlayerWalletProjection> getWalletBalance(Long idGathering) {
		log.info("Fetching wallet balance for gathering {}", idGathering);
		return repository.getWalletBalance(idGathering);
	}

	public List<PlayerTransactionProjection> getPlayerTransaciton(Long idGathering) {
		log.info("Fetching player transactions for gathering {}", idGathering);
		return repository.getPlayerTransaciton(idGathering);
	}

	public List<FormatProjection> getFormatProjection(Long idGathering) {
		log.info("Fetching formats for gathering {}", idGathering);
		return repository.getFormatProjection(idGathering);
	}

	public GatheringSummaryProjection getSummaryProjection(Long idGathering) {
		log.info("Fetching summary for gathering {}", idGathering);
		return repository.getSummaryProjection(idGathering);
	}

	public List<RankProjection> getRankProjection(Long idGathering) {
		List<RankProjection> list = repository.getRankProjection(idGathering);

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
	
}
