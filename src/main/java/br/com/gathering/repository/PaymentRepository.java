package br.com.gathering.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gathering.entity.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long>{

}
