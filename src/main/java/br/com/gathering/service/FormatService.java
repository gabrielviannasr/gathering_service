package br.com.gathering.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import br.com.gathering.entity.Format;
import br.com.gathering.repository.FormatRepository;

@Service
public class FormatService extends AbstractService<Format> {

	@Autowired
	private FormatRepository repository;
	
	public static Sort getSort() {
		return Sort.by(Order.asc("name"));
	}

	public List<Format> getList(Format Format) {
		return repository.findAll(getExample(Format), getSort());
	}

	public Format save(Format Format) {
		return repository.save(Format);
	}

}
