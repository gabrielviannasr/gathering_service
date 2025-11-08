package br.com.gathering.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.gathering.projection.gathering.PlayerTransactionProjection;
import br.com.gathering.projection.gathering.PlayerWalletProjection;
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
	
}
