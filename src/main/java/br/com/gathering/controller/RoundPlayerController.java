package br.com.gathering.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.gathering.dto.RoundPlayerDTO;
import br.com.gathering.entity.RoundPlayer;
import br.com.gathering.service.RoundPlayerService;

@RestController
@RequestMapping("/round-player")
public class RoundPlayerController {

	@Autowired
	private RoundPlayerService service;

	@GetMapping
	public List<RoundPlayer> getList(RoundPlayer model) {
		return service.getList(model);
	}

	@GetMapping("/{id}")
	public RoundPlayer getById(@PathVariable Long id) {
		System.out.println("id: " + id);
		return service.getById(id);
	}

	@PostMapping
	public RoundPlayer save(@RequestBody RoundPlayerDTO dto) {
		System.out.println(dto);
		RoundPlayer model = dto.toModel();
		model.init();
		System.out.println(model);
		return service.save(model);
	}

	@PutMapping
	public RoundPlayer update(@RequestParam Long id, @RequestBody RoundPlayerDTO dto) {
		System.out.println(dto);
		RoundPlayer model = dto.toModel();
		model.setId(id);
		model.init();
		System.out.println(model);
		return service.save(model);
	}

}
