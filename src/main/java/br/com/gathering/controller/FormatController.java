package br.com.gathering.controller;

import java.util.List;

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

@RestController
@RequestMapping("/format")
public class FormatController {

	@Autowired
	private FormatService service;

	@GetMapping
	public List<Format> getList(Format model) {
		return service.getList(model);
	}

	@GetMapping("/{id}")
	public Format getById(@PathVariable Long id) {
		System.out.println("id: " + id);
		return service.getById(id);
	}

	@PostMapping
	public Format save(@RequestBody FormatDTO dto) {
		System.out.println(dto);
		Format model = dto.toModel();
		System.out.println(model);
		return service.save(model);
	}

	@PutMapping
	public Format update(@RequestParam Long id, @RequestBody FormatDTO dto) {
		System.out.println(dto);
		Format model = dto.toModel();
		model.setId(id);
		System.out.println(model);
		return service.save(model);
	}

}
