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

import br.com.gathering.dto.ScoreDTO;
import br.com.gathering.entity.Score;
import br.com.gathering.service.ScoreService;

@RestController
@RequestMapping("/score")
public class ScoreController {

	@Autowired
	private ScoreService service;

	@GetMapping
	public List<Score> getList(Score model) {
		return service.getList(model);
	}

	@GetMapping("/{id}")
	public Score getById(@PathVariable Long id) {
		System.out.println("id: " + id);
		return service.getById(id);
	}

	@PostMapping
	public Score save(@RequestBody ScoreDTO dto) {
		System.out.println(dto);
		Score model = dto.toModel();
		model.init();
		System.out.println(model);
		return service.save(model);
	}

	@PutMapping
	public Score update(@RequestParam Long id, @RequestBody ScoreDTO dto) {
		System.out.println(dto);
		Score model = dto.toModel();
		model.setId(id);
		model.init();
		System.out.println(model);
		return service.save(model);
	}

}
