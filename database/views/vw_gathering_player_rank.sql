CREATE OR REPLACE VIEW gathering.vw_gathering_player_rank AS
SELECT
	id_gathering,
	RANK() OVER (
-- PARTITION BY b.id_gathering garante que o ranking é calculado dentro de cada gathering individualmente.
-- (Sem isso, se houver várias gatherings, o RANK() pode comparar jogadores entre gatherings diferentes.)		
        PARTITION BY id_gathering
        ORDER BY COALESCE(SUM(positive - negative), 0) DESC,
                 COALESCE(SUM(rounds), 0) ASC
	) AS rank,
	id_player,
    player_name,
    COUNT(id_event) AS total, 
    COALESCE(SUM(wins), 0) AS wins,
    COALESCE(SUM(rounds), 0) AS rounds,
    COALESCE(SUM(positive), 0) AS positive,
    COALESCE(SUM(negative), 0) AS negative,
    COALESCE(SUM(positive - negative), 0) AS rank_balance
FROM
    gathering.vw_event_player_balance
GROUP BY
	id_gathering, id_player, player_name
ORDER BY
    id_gathering, rank, player_name;