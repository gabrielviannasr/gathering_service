package br.com.gathering.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gathering.entity.Gathering;

@Repository
public interface GatheringRepository extends JpaRepository<Gathering, Long>{

}
