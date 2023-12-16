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

import br.com.gathering.dto.TransactionDTO;
import br.com.gathering.entity.Player;
import br.com.gathering.entity.Transaction;
import br.com.gathering.service.PlayerService;
import br.com.gathering.service.TransactionService;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

	@Autowired
	private TransactionService service;

	@Autowired
	private PlayerService playerService;

	@GetMapping
	public List<Transaction> getList(Transaction model) {
		System.out.println(model);
		return service.getList(model);
	}

	@GetMapping("/id")
	public Transaction getById(@RequestParam Long id) {
		System.out.println("id: " + id);
		return service.getById(id);
	}

	@PostMapping
	public Transaction save(@RequestBody TransactionDTO dto) {
		System.out.println(dto);
		Transaction model = dto.toModel();
		System.out.println(model);
		model = service.save(model);

		playerService.updateWallet(model.getIdPlayer());

		// Fetch the managed entity to ensure it's in the persistence context
		return getById(model.getId());
	}

	@PutMapping
	public Transaction update(@RequestParam Long id, @RequestBody TransactionDTO dto) {
		System.out.println(dto);
		Transaction model = dto.toModel();
		model.setId(id);
		System.out.println(model);		
		model = service.save(model);

		playerService.updateWallet(model.getIdPlayer());

		// Fetch the managed entity to ensure it's in the persistence context
		return getById(model.getId());
	}

}
