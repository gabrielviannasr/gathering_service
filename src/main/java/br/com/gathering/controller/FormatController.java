package br.com.gathering.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

	@PostMapping
	public Format save(@RequestBody Format format) {
		System.out.println(format);
		return service.save(format);
	}

	@PutMapping
	public Format update(@RequestBody Format format) {
		System.out.println(format);
		return service.save(format);
	}

}
