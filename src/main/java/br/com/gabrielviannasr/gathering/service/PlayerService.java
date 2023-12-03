package br.com.gabrielviannasr.gathering.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import br.com.gabrielviannasr.gathering.entity.Player;
import br.com.gabrielviannasr.gathering.repository.PlayerRepository;

@Service
public class PlayerService extends AbstractService<Player> {

	@Autowired
	private PlayerRepository repository;
	
	public static Sort getSort() {
		return Sort.by(Order.asc("name"), Order.asc("username"));
	}

	public List<Player> getList(Player player) {
		return repository.findAll(getExample(player), getSort());
	}

	public Player save(Player player) {
		return repository.save(player);
	}

}
