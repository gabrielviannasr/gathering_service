package br.com.gathering.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.gathering.entity.Round;
import br.com.gathering.repository.RoundRepository;

@Service
public class RoundService extends AbstractService<Round> {

	@Autowired
	private RoundRepository repository;

	public static Sort getSort() {
		return Sort.by(Order.asc("createdAt"));
	}

	public List<Round> getList(Round round) {
		return repository.findAll(getExample(round), getSort());
	}

	public Round getById(Long id) {
		Optional<Round> optional = repository.findById(id);
		return optional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	public Round save(Round round) {
		return repository.save(round);
	}

}
