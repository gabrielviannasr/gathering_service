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

import br.com.gathering.entity.Transaction;
import br.com.gathering.repository.TransactionRepository;
import jakarta.persistence.EntityManager;

@Service
public class TransactionService extends AbstractService<Transaction> {

	@Autowired
	private TransactionRepository repository;

	@Autowired
	private EntityManager entityManager;

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

		Transaction transaction = repository.save(model);
		// Clear the persistence context
		entityManager.clear();

		// Fetch the managed entity to ensure it's in the persistence context
		transaction = getById(transaction.getId());
		return transaction;		
	}

	private void validate(Transaction model) {

	}

}
