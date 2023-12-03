package br.com.gathering.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

	@PostMapping
	public Event save(@RequestBody Event event) {
		event.init();
		System.out.println(event);
		return service.save(event);
	}

	@PutMapping
	public Event update(@RequestBody Event event) {
		System.out.println(event);
		return service.save(event);
	}

}
