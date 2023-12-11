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

import br.com.gathering.dto.GatheringDTO;
import br.com.gathering.entity.Gathering;
import br.com.gathering.service.GatheringService;

@RestController
@RequestMapping("/gathering")
public class GatheringController {

	@Autowired
	private GatheringService service;

	@GetMapping
	public List<Gathering> getList(Gathering model) {
		System.out.println(model);
		return service.getList(model);
	}

	@GetMapping("/id")
	public Gathering getById(@RequestParam Long id) {
		System.out.println("id: " + id);
		return service.getById(id);
	}

	@PostMapping
	public Gathering save(@RequestBody GatheringDTO dto) {
		System.out.println(dto);
		Gathering model = dto.toModel();
		System.out.println(model);
		return service.save(model); 
	}

	@PutMapping
	public Gathering update(@RequestParam Long id, @RequestBody GatheringDTO dto) {
		System.out.println(dto);
		Gathering model = dto.toModel();
		model.setId(id);
		System.out.println(model);
		return service.save(model);
	}

}