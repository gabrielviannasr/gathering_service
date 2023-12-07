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
import br.com.gathering.entity.Rank;
import br.com.gathering.service.EventService;

@RestController
@RequestMapping("/event")
public class EventController {

	@Autowired
	private EventService service;

	@GetMapping
	public List<Event> getList(Event model) {
		System.out.println(model);
		return service.getList(model);
	}

	@GetMapping("/id")
	public Event getById(@RequestParam Long id) {
		System.out.println("id: " + id);
		return service.getById(id);
	}

//	@GetMapping("/id/rank")
//	public List<RankProjection> getRank(@RequestParam Long id) {
//		System.out.println("id: " + id);
//		return service.getRank(id);
//	}
	@GetMapping("/id/rank")
	public List<Rank> getRank(@RequestParam Long id) {
		System.out.println("id: " + id);
		return service.getRank(id);
	}

	@PostMapping
	public Event save(@RequestBody EventDTO dto) {
		System.out.println(dto);
		Event model = dto.toModel();
		System.out.println(model);
		return service.save(model);
	}

	@PutMapping
	public Event update(@RequestParam Long id, @RequestBody EventDTO dto) {
		System.out.println(dto);
		Event model = dto.toModel();
		model.setId(id);
		System.out.println(model);
		return service.save(model);
	}

}
