package br.com.gathering.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.gathering.entity.Event;
import br.com.gathering.projection.ConfraPotProjection;
import br.com.gathering.projection.LoserPotProjection;
import br.com.gathering.projection.RankCountProjection;
import br.com.gathering.projection.RankProjection;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>{
	
	@Query(nativeQuery = true, value = """
			SELECT
				id_event AS idEvent,
				players,
				confra_pot AS confraPot
			FROM
				gathering.vw_event_confra_pot
			WHERE
			    id_event = :idEvent
			""")
	ConfraPotProjection getConfraPot(@Param("idEvent") Long idEvent);

	@Query(nativeQuery = true, value = """
			SELECT
			    id_event AS idEvent,
			    rounds,
			    loser_pot AS loserPot
			FROM
			    gathering.vw_event_loser_pot
			WHERE
			    id_event = :idEvent
			""")
	LoserPotProjection getLoserPot(@Param("idEvent") Long idEvent);

	@Query(nativeQuery = true, value = """
			SELECT
			    id_event AS idEvent,
			    rank,
			    count
			FROM
			    gathering.vw_event_rank_count
			WHERE
			    id_event = :idEvent
			""")
	List<RankCountProjection> getRankCount(Long idEvent);

	@Query(nativeQuery = true, value = """
			SELECT
				id_event AS idEvent,
				rank,
				id_player AS idPlayer,
				player_name AS playerName,
				wins,
				rounds,
				positive,
				negative,
				rank_balance AS rankBalance
			FROM
			    gathering.vw_event_player_rank
			WHERE
				id_event = :idEvent
			""")
	List<RankProjection> getRankProjection(Long idEvent);

}
