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

import br.com.gathering.dto.PaymentDTO;
import br.com.gathering.entity.Payment;
import br.com.gathering.service.PaymentService;

@RestController
@RequestMapping("/payment")
public class PaymentController {

	@Autowired
	private PaymentService service;

	@GetMapping
	public List<Payment> getList(Payment model) {
		System.out.println(model);
		return service.getList(model);
	}

	@GetMapping("/id")
	public Payment getById(@RequestParam Long id) {
		System.out.println("id: " + id);
		return service.getById(id);
	}

	@PostMapping
	public Payment save(@RequestBody PaymentDTO dto) {
		System.out.println(dto);
		Payment model = dto.toModel();
		System.out.println(model);
		return service.save(model); 
	}

	@PutMapping
	public Payment update(@RequestParam Long id, @RequestBody PaymentDTO dto) {
		System.out.println(dto);
		Payment model = dto.toModel();
		model.setId(id);
		System.out.println(model);
		return service.save(model);
	}

}
