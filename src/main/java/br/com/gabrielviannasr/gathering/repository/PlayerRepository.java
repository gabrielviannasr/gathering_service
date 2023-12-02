package br.com.gabrielviannasr.gathering.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gabrielviannasr.gathering.entity.Player;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long>{

}
