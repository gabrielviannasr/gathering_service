package br.com.gathering.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.gathering.dto.EventDTO;
import br.com.gathering.entity.Event;
import br.com.gathering.service.EventService;

@RestController
@RequestMapping("/event")
public class EventController {

	@Autowired
	private EventService service;

	@GetMapping
	public List<Event> getList(Event event) {
		System.out.println(event);
		return service.getList(event);
	}

	@GetMapping("/id")
	public Event getById(@RequestParam Long id) {
		System.out.println("id: " + id);
		return service.getById(id);
	}

	@PostMapping
	public Event save(@RequestBody EventDTO dto) {
		System.out.println(dto);
		Event event = dto.toEvent();
		System.out.println(event);
		return service.save(event);
	}

	@PutMapping
	public Event update(@RequestParam Long id, @RequestBody EventDTO dto) {
		System.out.println(dto);
		Event event = dto.toEvent();
		event.setId(id);
		System.out.println(event);
		return service.save(event);
	}

}
