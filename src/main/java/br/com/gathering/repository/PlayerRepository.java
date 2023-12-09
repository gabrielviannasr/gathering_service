package br.com.gathering.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.gathering.entity.Player;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long>{

	@Query(nativeQuery = true, value = "SELECT * FROM gathering.update_wallet(:id);")
	Player updateWallet(Long id);

}
