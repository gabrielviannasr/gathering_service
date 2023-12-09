package br.com.gathering.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.gathering.entity.Payment;
import br.com.gathering.repository.PaymentRepository;

@Service
public class PaymentService extends AbstractService<Payment> {

	@Autowired
	private PaymentRepository repository;

	public static Sort getSort() {
		return Sort.by(Order.asc("createdAt"));
	}

	public List<Payment> getList(Payment model) {
		return repository.findAll(getExample(model), getSort());
	}

	public Payment getById(Long id) {
		Optional<Payment> optional = repository.findById(id);
		return optional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	public Payment save(Payment model) {
		model.init();
		return repository.save(model);
	}

}
