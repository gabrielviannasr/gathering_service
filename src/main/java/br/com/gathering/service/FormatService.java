package br.com.gathering.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.gathering.entity.Format;
import br.com.gathering.repository.FormatRepository;
import br.com.gathering.util.LogHelper;

@Service
public class FormatService extends AbstractService<Format> {

	private static final Logger log = LogHelper.getLogger();

	@Autowired
	private FormatRepository repository;

	public static Sort getSort() {
		return Sort.by(Order.asc("name"));
	}

	public List<Format> getList(Format model) {
		List<Format> result = repository.findAll(getExample(model), getSort());
        LogHelper.info(log, "Fetched list", "count", result.size());
        return result;
	}

	public Format getById(Long id) {
		LogHelper.info(log, "Fetching by ID", "id", id);
        Optional<Format> optional = repository.findById(id);
        if (optional.isEmpty()) {
            LogHelper.warn(log, "Not found", "id", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Format event = optional.get();
        LogHelper.info(log, "Found", "id", event.getId());
        return event;
	}

	public Format save(Format model) {
		model.init();
//		validate(model);
		LogHelper.info(log, "Saving", "payload", model);
		Format saved = repository.save(model);
        LogHelper.info(log, "Saved", "id", saved.getId());
        return saved;
	}

}
