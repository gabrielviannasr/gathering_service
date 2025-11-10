package br.com.gathering.controller;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.gathering.dto.FormatDTO;
import br.com.gathering.entity.Format;
import br.com.gathering.service.FormatService;
import br.com.gathering.util.LogHelper;

@RestController
@RequestMapping("/format")
public class FormatController {

	private static final Logger log = LogHelper.getLogger(FormatController.class);
	
	@Autowired
	private FormatService service;

	@GetMapping
	public List<Format> getList(Format model) {
		LogHelper.info(log, "GET /format", "Format", model);
		return service.getList(model);
	}

	@GetMapping("/{id}")
	public Format getById(@PathVariable Long id) {
		LogHelper.info(log, "GET /format/{id}", "id", id);
		return service.getById(id);
	}

	@PostMapping
	public Format save(@RequestBody FormatDTO dto) {
		Format model = dto.toModel();
		LogHelper.info(log, "POST /format", "payload", model);
		return service.save(model);
	}

	@PutMapping
	public Format update(@RequestParam Long id, @RequestBody FormatDTO dto) {
		System.out.println(dto);
		Format model = dto.toModel();
		model.setId(id);
		LogHelper.info(log, "PUT /format", "id", id, "payload", model);
		return service.save(model);
	}

}
