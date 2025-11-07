package br.com.gathering.service;

import java.util.Arrays;
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

import br.com.gathering.constant.TransactionType;
import br.com.gathering.entity.Transaction;
import br.com.gathering.projection.gathering.PlayerWalletProjection;
import br.com.gathering.repository.GatheringRepository;
import br.com.gathering.repository.PlayerRepository;
import br.com.gathering.repository.TransactionRepository;

@Service
public class TransactionService extends AbstractService<Transaction> {

	@Autowired
	private TransactionRepository repository;

	@Autowired
	private GatheringRepository gatheringRepository;

	@Autowired
	private PlayerRepository playerRepository;

	public static Sort getSort() {
		return Sort.by(Order.asc("idGathering"), Order.asc("idPlayer"), Order.asc("createdAt"));
	}

	public List<Transaction> getList(Transaction model) {
		return repository.findAll(getExample(model), getSort());
	}

	public Page<Transaction> getPage(Transaction model, Sort sort, int page, int size) {
		return repository.findAll(getExample(model), PageRequest.of(page, size, sort));
	}

	public Transaction getById(Long id) {
		Optional<Transaction> optional = repository.findById(id);
		return optional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	public Transaction save(Transaction model) {
		model.init();
		validate(model);
		return model;		
	}

	private void validate(Transaction model) {

	    if (model.getIdGathering() == null)
	        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID da gathering é obrigatório");

	    if (model.getIdPlayer() == null)
	        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID do player é obrigatório");

	    if (model.getIdTransactionType() == null)
	        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID do tipo de transação é obrigatório");

	    if (!gatheringRepository.existsById(model.getIdGathering())) {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Gathering não encontrada");
	    }

	    if (!playerRepository.existsById(model.getIdPlayer())) {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Jogador não encontrado");
	    }

	    TransactionType type = Arrays.stream(TransactionType.values())
	        .filter(t -> t.getId() == model.getIdTransactionType())
	        .findFirst()
	        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tipo de transação não encontrado"));

	    switch (type) {
	        case INSCRICAO, RESULTADO -> {
	            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
	                "Transações de inscrição e resultado são geradas automaticamente pelo sistema");
	        }
	        case DEPOSITO -> {
	            if (model.getAmount() == null || model.getAmount() <= 0)
	                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Valor do depósito deve ser positivo");
	        }
	        case SAQUE -> {
	            if (model.getAmount() == null || model.getAmount() >= 0)
	                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Valor do saque deve ser negativo");

	            Double wallet = getWalletBalance(model.getIdGathering(), model.getIdPlayer());

	            if (wallet + model.getAmount() < 0)
	                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Saldo insuficiente para saque");
	        }
	    }
	}

	public double getWalletBalance(Long idGathering, Long idPlayer) {
	    PlayerWalletProjection walletProjection = repository.getWalletBalance(idGathering, idPlayer);
	    return (walletProjection != null && walletProjection.getWallet() != null)
	        ? walletProjection.getWallet()
	        : 0.0; // In case of first event, so without transactions
	}

}
