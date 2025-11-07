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

import br.com.gathering.constant.TransactionType;
import br.com.gathering.entity.Transaction;
import br.com.gathering.repository.TransactionRepository;
import jakarta.persistence.EntityManager;

@Service
public class TransactionService extends AbstractService<Transaction> {

	@Autowired
	private TransactionRepository repository;

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

		if (model.getIdGathering() == null) {

			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID da gathering é obrigatório");

		}
		if (model.getIdPlayer() == null) {

			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID do player é obrigatório");

		}

		Long idTransactionType = model.getIdTransactionType();

		if (idTransactionType == null) {

			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID do tipo de transação é obrigatório");

		} else if (idTransactionType == TransactionType.INSCRICAO.getId() || idTransactionType == TransactionType.RESULTADO.getId()) {

			if (model.getIdEvent() == null) {

				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID do evento é obrigatório");

			}

		} else if (idTransactionType == TransactionType.DEPOSITO.getId()) {

			if(model.getAmount() < 0) {

				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Valor do depósito deve ser positivo");

			}

		} else if (idTransactionType == TransactionType.SAQUE.getId()) {

			if(model.getAmount() >= 0) {

				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Valor do saque deve ser negativo");

			}

			Double wallet = 0.0; // TODO: Consultar do repository

			if (wallet + model.getAmount() < 0) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Saldo insuficiente para saque");
			}

		} else {

			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tipo de transação não encontrada");

		}

	}

}
