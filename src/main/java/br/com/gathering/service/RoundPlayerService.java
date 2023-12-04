package br.com.gathering.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.gathering.entity.RoundPlayer;
import br.com.gathering.repository.RoundPlayerRepository;

@Service
public class RoundPlayerService extends AbstractService<RoundPlayer> {

	@Autowired
	private RoundPlayerRepository repository;

	public static Sort getSort() {
		return Sort.by(Order.asc("id"));
	}

	public List<RoundPlayer> getList(RoundPlayer roundPlayer) {
		return repository.findAll(getExample(roundPlayer), getSort());
	}

	public RoundPlayer getById(Long id) {
		Optional<RoundPlayer> optional = repository.findById(id);
		return optional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	public RoundPlayer save(RoundPlayer roundPlayer) {
		roundPlayer.init();
		return repository.save(roundPlayer);
	}

}
