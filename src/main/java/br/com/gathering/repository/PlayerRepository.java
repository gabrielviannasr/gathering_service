package br.com.gathering.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gathering.entity.Player;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long>{

}
