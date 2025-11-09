-- Player Balance used as base for rank calculation
SELECT
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
    e.id = :idEvent
    AND r.canceled = false
GROUP BY
    p.id, e.round_fee
ORDER BY
    p.name;

-- Player Balance using view (vw_event_player_balance)
SELECT
    id_player,
    player_name,
    wins,
    rounds,
    positive,
    negative,
    positive - negative AS rank_balance
FROM
    gathering.vw_event_player_balance
WHERE
	id_event = :idEvent
ORDER BY
    player_name;