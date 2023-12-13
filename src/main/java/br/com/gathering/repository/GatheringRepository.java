package br.com.gathering.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.gathering.entity.Gathering;
import br.com.gathering.projection.DashboardProjection;

@Repository
public interface GatheringRepository extends JpaRepository<Gathering, Long>{

	@Query(nativeQuery = true, value = ""
			+ "SELECT\r\n"
			+ "	id_player AS idPlayer,\r\n"
			+ "	username,\r\n"
			+ "	events_balance AS eventsBalance,\r\n"
			+ "	transactions_balance AS transactionsBalance,\r\n"
			+ "	events_balance + transactions_balance AS finalBalance\r\n"
			+ "FROM (\r\n"
			+ "	SELECT\r\n"
			+ "		player.id AS id_player,\r\n"
			+ "		player.username,\r\n"
			+ "		COALESCE(SUM(rank.final_balance), 0) AS events_balance,\r\n"
			+ "		COALESCE(SUM(CASE WHEN transaction.id_gathering = :idGathering THEN transaction.amount END), 0) AS transactions_balance\r\n"
			+ "	FROM gathering.player player		\r\n"
			+ "		INNER JOIN gathering.rank rank ON player.id = rank.id_player\r\n"
			+ "		INNER JOIN gathering.event event ON event.id = rank.id_event\r\n"
			+ "		FULL OUTER JOIN gathering.transaction transaction ON player.id = transaction.id_player\r\n"
			+ "	WHERE\r\n"
			+ "	 	event.id_gathering = :idGathering\r\n"
			+ "	GROUP BY player.id, player.username\r\n"
			+ "	ORDER BY player.username\r\n"
			+ ") AS subquery;")
	List<DashboardProjection> getDashboard(Long idGathering);

}
