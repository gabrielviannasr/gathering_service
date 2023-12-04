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

import br.com.gathering.dto.PlayerDTO;
import br.com.gathering.entity.Player;
import br.com.gathering.service.PlayerService;

@RestController
@RequestMapping("/player")
public class PlayerController {

	@Autowired
	private PlayerService service;

	@GetMapping
	public List<Player> getList(Player model) {
		System.out.println(model);
		return service.getList(model);
	}

	@GetMapping("/id")
	public Player getById(@RequestParam Long id) {
		System.out.println("id: " + id);
		return service.getById(id);
	}

	@PostMapping
	public Player save(@RequestBody PlayerDTO dto) {
		System.out.println(dto);
		Player model = dto.toModel();
		System.out.println(model);
		return service.save(model);
	}

	@PutMapping
	public Player update(@RequestParam Long id, @RequestBody PlayerDTO dto) {
		System.out.println(dto);
		Player model = dto.toModel();
		model.setId(id);
		System.out.println(model);
		return service.save(model);
	}

}
