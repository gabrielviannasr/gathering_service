package br.com.gathering.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
	public List<Format> getList(Format format) {
		return service.getList(format);
	}

	@GetMapping("/id")
	public Format getById(@RequestParam Long id) {
		System.out.println("id: " + id);
		return service.getById(id);
	}

	@PostMapping
	public Format save(@RequestBody FormatDTO dto) {
		System.out.println(dto);
		Format format = dto.toFormat();
		System.out.println(format);
		return service.save(format);
	}

	@PutMapping
	public Format update(@RequestParam Long id, @RequestBody FormatDTO dto) {
		System.out.println(dto);
		Format format = dto.toFormat();
		format.setId(id);
		System.out.println(format);
		return service.save(format);
	}

}
