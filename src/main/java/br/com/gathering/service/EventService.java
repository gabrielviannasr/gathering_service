package br.com.gathering.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import br.com.gathering.entity.Event;
import br.com.gathering.repository.EventRepository;

@Service
public class EventService extends AbstractService<Event> {

	@Autowired
	private EventRepository repository;

	public static Sort getSort() {
		return Sort.by(Order.asc("createdAt"));
	}

	public List<Event> getList(Event event) {
		return repository.findAll(getExample(event), getSort());
	}

	public Event save(Event event) {
		return repository.save(event);
	}

}
