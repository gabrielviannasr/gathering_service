package br.com.gathering.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.gathering.entity.Format;
import br.com.gathering.repository.FormatRepository;

@Service
public class FormatService extends AbstractService<Format> {

	@Autowired
	private FormatRepository repository;

	public static Sort getSort() {
		return Sort.by(Order.asc("name"));
	}

	public List<Format> getList(Format model) {
		return repository.findAll(getExample(model), getSort());
	}

	public Format getById(Long id) {
		Optional<Format> optional = repository.findById(id);
		return optional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	public Format save(Format model) {
//		validate(model);
		return repository.save(model);
	}

}
