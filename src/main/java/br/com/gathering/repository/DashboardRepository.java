package br.com.gathering.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.gathering.projection.gathering.PlayerWalletProjection;

@Repository
public interface DashboardRepository extends ViewRepository {

    @Query(nativeQuery = true, value = """
    	    SELECT
				-- id_gathering AS idGathering,
				-- gathering_name AS gatheringName,
				id_player AS idPlayer,
				player_name AS playerName,
				wallet
			FROM
				gathering.vw_gathering_player_wallet
			WHERE
				id_gathering = :idGathering
			ORDER BY
				id_gathering, player_name
    	""")
    List<PlayerWalletProjection> getWalletBalance(@Param("idGathering") Long idGathering);

}
