package br.com.gathering.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gathering.entity.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>{
	
}
