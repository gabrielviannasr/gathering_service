package br.com.gathering.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.gathering.projection.RankProjection;
import br.com.gathering.entity.Event;
import br.com.gathering.repository.EventRepository;

@Service
public class EventService extends AbstractService<Event> {

	@Autowired
	private EventRepository repository;

	public static Sort getSort() {
		return Sort.by(Order.asc("createdAt"));
	}

	public List<Event> getList(Event model) {
		return repository.findAll(getExample(model), getSort());
	}

	public Event getById(Long id) {
		Optional<Event> optional = repository.findById(id);
		return optional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	public List<RankProjection> getRank(Long idEvent) {
		return repository.getRank(idEvent);
	}

	public Event save(Event model) {
		model.init();
		return repository.save(model);
	}

}
