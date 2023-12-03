package br.com.gabrielviannasr.gathering.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gabrielviannasr.gathering.entity.Player;
import br.com.gabrielviannasr.gathering.repository.PlayerRepository;

@Service
public class PlayerService {
	
	@Autowired
	private PlayerRepository repository;

	public List<Player> getList() {
		return repository.findAll();		
	}
	
	public Player save(Player player) {
		return repository.save(player);
	}
	
}
