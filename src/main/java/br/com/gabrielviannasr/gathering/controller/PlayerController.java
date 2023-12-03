package br.com.gabrielviannasr.gathering.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gabrielviannasr.gathering.entity.Player;
import br.com.gabrielviannasr.gathering.service.PlayerService;

@RestController
@RequestMapping("/player")
public class PlayerController {

	@Autowired
	private PlayerService service;

	@GetMapping
	public List<Player> getList() {
		return service.getList();
	}

	@PostMapping
	public Player save(@RequestBody Player player) {
		System.out.println(player);
		return service.save(player);
	}

	@PutMapping
	public Player update(@RequestBody Player player) {
		System.out.println(player);
		return service.save(player);
	}

}
