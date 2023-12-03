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
	public List<Player> getList(Player player) {
		System.out.println(player);
		return service.getList(player);
	}

	@GetMapping("/id")
	public Player getById(@RequestParam Long id) {
		System.out.println("id: " + id);
		return service.getById(id);
	}

	@PostMapping
	public Player save(@RequestBody PlayerDTO dto) {
		System.out.println(dto);
		Player player = dto.toPlayer();
		System.out.println(player);
		return service.save(player);
	}

	@PutMapping
	public Player update(@RequestParam Long id, @RequestBody PlayerDTO dto) {
		System.out.println(dto);
		Player player = dto.toPlayer();
		player.setId(id);
		System.out.println(player);
		return service.save(player);
	}

}
