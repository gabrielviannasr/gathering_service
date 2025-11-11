package br.com.gathering.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
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
import br.com.gathering.util.LogHelper;

@Service
public class EventService extends AbstractService<Event> {

	private static final Logger log = LogHelper.getLogger();

	@Autowired
	private EventRepository repository;

	public static Sort getSort() {
		return Sort.by(Order.asc("idGathering"), Order.asc("createdAt"));
	}

	public List<Event> getList(Event model) {
//		LogHelper.info(log, "Fetching list", "filter", model);
        List<Event> result = repository.findAll(getExample(model), getSort());
        LogHelper.info(log, "Fetched list", "count", result.size());
        return result;
	}

	public Page<Event> getPage(Event model, Sort sort, int page, int size) {
		LogHelper.info(log, "Fetching paged list", "page", page, "size", size);
        Page<Event> result = repository.findAll(getExample(model), PageRequest.of(page, size, sort));
        LogHelper.info(log, "Fetched paged list", "totalElements", result.getTotalElements());
        return result;
	}

	public Event getById(Long id) {
//		Optional<Event> optional = repository.findById(id);
//		return optional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		LogHelper.info(log, "Fetching by ID", "id", id);
        Optional<Event> optional = repository.findById(id);
        if (optional.isEmpty()) {
            LogHelper.warn(log, "Not found", "id", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Event event = optional.get();
        LogHelper.info(log, "Found", "id", event.getId());
        return event;
	}

	public Event save(Event model) {
		model.init();
		validate(model);
//		return repository.save(model);		
		LogHelper.info(log, "Saving", "payload", model);
        Event saved = repository.save(model);
        LogHelper.info(log, "Saved", "id", saved.getId());
        return saved;
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
		    	LogHelper.warn(log, "INVALID LOSER FEE", "players", players, "loserFee", loserFee, "total", total);

		        throw new ResponseStatusException(
		            HttpStatus.BAD_REQUEST,
		            String.format("LoserFee%d (%.2f) exceeds total amount available for a %d-player round (%.2f)",
		                players, loserFee, players, total)
		        );
		    }
		    LogHelper.warn(log, "VALID LOSER FEE", "players", players, "loserFee", loserFee, "total", total);
		}
	}

}
