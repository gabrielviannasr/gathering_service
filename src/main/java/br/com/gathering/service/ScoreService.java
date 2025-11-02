package br.com.gathering.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.gathering.entity.Score;
import br.com.gathering.repository.ScoreRepository;

@Service
public class ScoreService extends AbstractService<Score> {

	@Autowired
	private ScoreRepository repository;

	public static Sort getSort() {
		return Sort.by(Order.asc("id"));
	}

	public List<Score> getList(Score model) {
		return repository.findAll(getExample(model), getSort());
	}

	public Score getById(Long id) {
		Optional<Score> optional = repository.findById(id);
		return optional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	public Score save(Score model) {
		model.init();
		return repository.save(model);
	}

}
