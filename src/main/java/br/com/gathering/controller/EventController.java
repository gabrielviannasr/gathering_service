package br.com.gathering.controller;

import java.util.List;

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
import br.com.gathering.entity.Result;
import br.com.gathering.projection.ConfraPotProjection;
import br.com.gathering.projection.LoserPotProjection;
import br.com.gathering.projection.PotProjection;
import br.com.gathering.projection.RankCountProjection;
import br.com.gathering.projection.RankProjection;
import br.com.gathering.service.EventService;
import br.com.gathering.service.PlayerService;

@RestController
@RequestMapping("/event")
public class EventController {

	@Autowired
	private EventService service;

	@Autowired
	private PlayerService playerService;

	@GetMapping
	public List<Event> getList(Event model) {
		System.out.println(model);
		return service.getList(model);
	}

	@GetMapping("/page")
	public Page<Event> getPage(Event model,
			@SortDefault.SortDefaults({ @SortDefault(sort = "idGathering"), @SortDefault(sort = "createdAt") }) Sort sort,
			@RequestParam int page,
			@RequestParam int size) {
		return service.getPage(model, sort, page, size);
	}

	@GetMapping("/{id}")
	public Event getById(@PathVariable Long id) {
		System.out.println("id: " + id);
		return service.getById(id);
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
		
		Event event = getById(id);
		
		Event model = dto.toModel();
		model.setId(id);
//		model.setRanks(event.getRanks());
		
		System.out.println(model);
		return service.save(model);
	}

	@GetMapping("/id/pot")
	public PotProjection getPot(Long idEvent) {
		System.out.println("id: " + idEvent);
		return service.getPot(idEvent);
	}

//	@PutMapping("/id/rank")
//	public List<Rank> getRank(@RequestParam Long id) {
//		System.out.println("id: " + id);
//		Event event = service.getRank(id);
//
//		if (event == null) {
//			return null;
//		}
//
//		// Update returned player
//		event.getRanks().forEach(rank -> {
//			rank.setPlayer(playerService.updateWallet(rank.getIdPlayer()));
//		});
//
//		return event.getRanks();
//	}

	@GetMapping("/id/rank/count")
	public List<RankCountProjection> getRankCount2(@RequestParam Long id) {
		System.out.println("id: " + id);
		return service.getRankCount(id);
	}

	@GetMapping("/{id}/confra-pot")
	public ConfraPotProjection getConfraPot(@PathVariable Long id) {
		System.out.println("id: " + id);
		return service.getConfraPot(id);
	}

	@GetMapping("/{id}/loser-pot")
	public LoserPotProjection getLoserPot(@PathVariable Long id) {
		System.out.println("id: " + id);
		return service.getLoserPot(id);
	}
	
	@GetMapping("/{id}/rank-count")
	public List<RankCountProjection> getRankCount(@PathVariable Long id) {
		System.out.println("id: " + id);
		return service.getRankCount(id);
	}

	@GetMapping("/{id}/rank")
	public List<RankProjection> getRankProjection(@PathVariable Long id) {
		System.out.println("id: " + id);
		return service.getRankProjection(id);
	}
	
	@GetMapping("/{id}/result")
	public List<Result> getResult(@PathVariable Long id) {
		System.out.println("id: " + id);
		return service.getResult(id);
	}

}
