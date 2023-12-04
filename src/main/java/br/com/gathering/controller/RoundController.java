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

import br.com.gathering.dto.RoundDTO;
import br.com.gathering.entity.Round;
import br.com.gathering.service.RoundService;

@RestController
@RequestMapping("/round")
public class RoundController {

	@Autowired
	private RoundService service;

	@GetMapping
	public List<Round> getList(Round model) {
		return service.getList(model);
	}

	@GetMapping("/id")
	public Round getById(@RequestParam Long id) {
		System.out.println("id: " + id);
		return service.getById(id);
	}

	@PostMapping
	public Round save(@RequestBody RoundDTO dto) {
		System.out.println(dto);
		Round model = dto.toModel();
		System.out.println(model);
		return service.save(model);
	}

	@PutMapping
	public Round update(@RequestParam Long id, @RequestBody RoundDTO dto) {
		System.out.println(dto);
		Round model = dto.toModel();
		model.setId(id);
		System.out.println(model);
		return service.save(model);
	}

}
