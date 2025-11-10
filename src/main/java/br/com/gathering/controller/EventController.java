package br.com.gathering.controller;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.gathering.dto.EventDTO;
import br.com.gathering.entity.Event;
import br.com.gathering.service.EventService;
import br.com.gathering.util.LogHelper;

@RestController
@RequestMapping("/event")
public class EventController {

	private static final Logger log = LogHelper.getLogger(DashboardController.class);

	@Autowired
	private EventService service;

	@GetMapping
	public List<Event> getList(Event model) {
		LogHelper.info(log, "GET /event", "Event", model);
		return service.getList(model);
	}

	@GetMapping("/page")
	public Page<Event> getPage(Event model,
			@SortDefault.SortDefaults({ @SortDefault(sort = "idGathering"), @SortDefault(sort = "createdAt") }) Sort sort,
			@RequestParam int page,
			@RequestParam int size) {
		LogHelper.info(log, "GET /event/page", "page", page, "size", size);
		return service.getPage(model, sort, page, size);
	}

	@GetMapping("/{id}")
	public Event getById(@PathVariable Long id) {
		LogHelper.info(log, "GET /event/{id}", "id", id);
		return service.getById(id);
	}

	@PostMapping
	public Event save(@RequestBody EventDTO dto) {
		Event model = dto.toModel();
		LogHelper.info(log, "POST /event", "payload", model);
		return service.save(model);
	}

	@PutMapping
	public Event update(@RequestParam Long id, @RequestBody EventDTO dto) {
		Event model = dto.toModel();
		model.setId(id);
		LogHelper.info(log, "PUT /event", "id", id, "payload", model);
		return service.save(model);
	}

}
