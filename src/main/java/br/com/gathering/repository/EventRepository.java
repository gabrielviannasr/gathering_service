package br.com.gathering.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.gathering.entity.Event;
import br.com.gathering.projection.RankProjection;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>{
	
	@Query(nativeQuery = true, value = "SELECT\r\n"
			+ "    RANK() OVER (ORDER BY wins DESC, rounds) AS rank,\r\n"
			+ "    id_player AS \"idPlayer\",\r\n"
			+ "    username,\r\n"
			+ "    wins,\r\n"
			+ "    rounds,\r\n"
			+ "    positive,\r\n"
			+ "    -negative AS negative,\r\n"
			+ "    positive - negative AS \"rankBalance\",\r\n"
			+ "    -prize_taken AS \"prizeTaken\",\r\n"
			+ "    positive - negative - prize_taken AS \"finalBalance\"\r\n"
			+ "FROM (\r\n"
			+ "    SELECT\r\n"
			+ "        p.id AS id_player,\r\n"
			+ "        p.username,\r\n"
			+ "        COUNT(CASE WHEN rp.rank = 1 THEN 1 END) AS wins,\r\n"
			+ "        COUNT(rp.id_player) AS rounds,\r\n"
			+ "        COUNT(CASE WHEN rp.rank = 1 THEN 1 END) * e.registration_fee * 4 AS positive,\r\n"
			+ "        COUNT(rp.id_player) * e.registration_fee AS negative,\r\n"
			+ "        SUM (CASE WHEN rp.rank = 1 THEN r.prize_taken ELSE 0 END) AS prize_taken\r\n"
			+ "    FROM\r\n"
			+ "        gathering.round_player rp\r\n"
			+ "        INNER JOIN gathering.round r ON r.id = rp.id_round\r\n"
			+ "        INNER JOIN gathering.event e ON e.id = r.id_event\r\n"
			+ "        INNER JOIN gathering.player p ON p.id = rp.id_player\r\n"
			+ "    WHERE\r\n"
			+ "        e.id = :idEvent\r\n"
			+ "        AND r.canceled = false\r\n"
			+ "    GROUP BY\r\n"
			+ "        p.id, p.username, e.registration_fee\r\n"
			+ ") AS subquery\r\n"
			+ "ORDER BY\r\n"
			+ "    rank, username;")
	List<RankProjection> getRank(Long idEvent);

}
