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

import br.com.gathering.entity.Event;
import br.com.gathering.repository.EventRepository;

@Service
public class EventService extends AbstractService<Event> {

	@Autowired
	private EventRepository repository;

	public static Sort getSort() {
		return Sort.by(Order.asc("idGathering"), Order.asc("createdAt"));
	}

	public List<Event> getList(Event model) {
		return repository.findAll(getExample(model), getSort());
	}

	public Page<Event> getPage(Event model, Sort sort, int page, int size) {
		return repository.findAll(getExample(model), PageRequest.of(page, size, sort));
	}

	public Event getById(Long id) {
		Optional<Event> optional = repository.findById(id);
		return optional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	public Event save(Event model) {
		model.init();
		validate(model);
		return repository.save(model);
	}
	
	private void validate(Event model) {
		
		for (int players = 4; players <= 6; players++) {
		    Double total = players * model.getRoundFee();
		    Double loserFee = switch (players) {
		        case 4 -> model.getLoserFee4();
		        case 5 -> model.getLoserFee5();
		        case 6 -> model.getLoserFee6();
		        default -> 0.0;
		    };
		    if (loserFee > total) {
		        throw new ResponseStatusException(
		            HttpStatus.BAD_REQUEST,
		            String.format("LoserFee%d (%.2f) exceeds total amount available for a %d-player round (%.2f)",
		                players, loserFee, players, total)
		        );
		    }
		}
	}
}
