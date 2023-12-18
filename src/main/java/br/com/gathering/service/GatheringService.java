package br.com.gathering.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.gathering.entity.Gathering;
import br.com.gathering.projection.DashboardProjection;
import br.com.gathering.repository.GatheringRepository;
import jakarta.persistence.EntityManager;

@Service
public class GatheringService extends AbstractService<Gathering> {

	@Autowired
	private GatheringRepository repository;

	@Autowired
	private EntityManager entityManager;

	public static Sort getSort() {
		return Sort.by(Order.asc("name"));
	}

	public List<Gathering> getList(Gathering model) {
		return repository.findAll(getExample(model), getSort());
	}

	public Page<Gathering> getPage(Gathering model, Sort sort, int page, int size) {
		return repository.findAll(getExample(model), PageRequest.of(page, size, sort));
	}

	public Gathering getById(Long id) {
		Optional<Gathering> optional = repository.findById(id);
		return optional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	public Gathering save(Gathering model) {
		model.init();
		repository.save(model);
		
		// Clear the persistence context
	    entityManager.clear();

		return getById(model.getId());
	}
	
	public List<DashboardProjection> getDashboard(Long idGathering) {
		return repository.getDashboard(idGathering);
	}

}
