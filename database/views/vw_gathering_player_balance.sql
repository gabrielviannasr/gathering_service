CREATE OR REPLACE VIEW gathering.vw_gathering_player_balance AS
SELECT
    id_gathering,
    id_player,
    player_name,
    COUNT(id_event) AS total_events,
    COALESCE(SUM(wins), 0) AS wins,
    COALESCE(SUM(rounds), 0) AS rounds,
    COALESCE(SUM(positive), 0) AS positive,
    COALESCE(SUM(negative), 0) AS negative,
    COALESCE(SUM(rank_balance), 0) AS rank_balance
FROM
    gathering.vw_event_player_balance
GROUP BY
	id_gathering, id_player, player_name
ORDER BY
    id_gathering, player_name;

COMMENT ON VIEW gathering.vw_gathering_player_balance IS
'Aggregates player balances across all events within each gathering. 
Serves as the base view for calculating the cumulative gathering-level rankings.';