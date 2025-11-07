package br.com.gathering.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.gathering.dto.TransactionDTO;
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

	@GetMapping("/page")
	public Page<Transaction> getPage(Transaction model,
			@SortDefault.SortDefaults({ @SortDefault(sort = "idPlayer"), @SortDefault(sort = "idGathering"), @SortDefault(sort = "idTransactionType"), @SortDefault(sort = "createdAt") }) Sort sort,
			@RequestParam int page,
			@RequestParam int size) {
		return service.getPage(model, sort, page, size);
	}

	@GetMapping("/{id}")
	public Transaction getById(@PathVariable Long id) {
		System.out.println("id: " + id);
		return service.getById(id);
	}

	@PostMapping
	public Transaction save(@RequestBody TransactionDTO dto) {
		System.out.println(dto);
		Transaction model = dto.toModel();
		System.out.println(model);
		return service.save(model);
	}

	@PutMapping
	public Transaction update(@RequestParam Long id, @RequestBody TransactionDTO dto) {
		System.out.println(dto);
		Transaction model = dto.toModel();
		model.setId(id);
		System.out.println(model);
		return service.save(model);
	}

}
