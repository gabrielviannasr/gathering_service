package br.com.gathering.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gathering.entity.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>{

    void deleteByIdEvent(Long idEvent);
    
    List<Transaction> findByIdEvent(Long idEvent);

}
