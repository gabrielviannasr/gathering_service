-- Confra Pot
SELECT
    e.id AS id_event,
    COUNT(DISTINCT s.id_player) AS players,
    COUNT(DISTINCT s.id_player) * e.confra_fee AS confra_pot
FROM
    gathering.score s
    INNER JOIN gathering.round r ON r.id = s.id_round
    INNER JOIN gathering.player p ON p.id = s.id_player
    INNER JOIN gathering.event e ON e.id = r.id_event
WHERE
    r.canceled = false
    AND id_event = :idEvent
GROUP BY
    e.id;

-- Confra Pot using view
SELECT
	id_event,
	players,
	confra_pot
FROM
	gathering.vw_event_confra_pot
WHERE
    id_event = :idEvent;