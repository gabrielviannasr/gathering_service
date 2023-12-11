package br.com.gathering.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.gathering.entity.Player;
import br.com.gathering.repository.PlayerRepository;
import jakarta.persistence.EntityManager;

@Service
public class PlayerService extends AbstractService<Player> {

	@Autowired
	private PlayerRepository repository;

	@Autowired
	private EntityManager entityManager;

	public static Sort getSort() {
		return Sort.by(Order.asc("name"), Order.asc("username"));
	}

	public List<Player> getList(Player model) {
		return repository.findAll(getExample(model), getSort());
	}

	public Player getById(Long id) {
		Optional<Player> optional = repository.findById(id);
		return optional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	public Player save(Player model) {
		model.init();
		return repository.save(model);
	}

	public Player updateWallet(Long id) {
		Player player = repository.updateWallet(id);

		// Clear the persistence context
	    entityManager.clear();
	    
	    // Fetch the managed entity to ensure it's in the persistence context
	    player = getById(id);

		System.out.println("updateWallet: " + player);
		return player;
	}

}
