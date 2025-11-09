package br.com.gathering.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gathering.entity.Score;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long>{

}
