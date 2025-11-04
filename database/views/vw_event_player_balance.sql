CREATE OR REPLACE VIEW gathering.vw_event_player_balance AS
SELECT
	e.id_gathering,
	e.id AS id_event,
    p.id AS id_player,
    p.name AS player_name,
    COUNT(CASE WHEN r.id_player_winner = p.id THEN 1 END) AS wins,
    COUNT(s.id_player) AS rounds,
    COALESCE(SUM(r.prize) FILTER (WHERE r.id_player_winner = p.id), 0) AS positive,
    -- SUM(CASE WHEN r.id_player_winner = p.id THEN r.prize ELSE 0 END) AS positive,
    COUNT(s.id_player) * e.round_fee AS negative
FROM
    gathering.score s
    INNER JOIN gathering.round r ON r.id = s.id_round
    INNER JOIN gathering.player p ON p.id = s.id_player
    INNER JOIN gathering.event e ON e.id = r.id_event
WHERE
    r.canceled = false
GROUP BY
    e.id, p.id
ORDER BY
	p.name;

COMMENT ON VIEW gathering.vw_event_player_balance IS
'Provides the balance of each player per event.
Used as the base view for event rank calculations.';