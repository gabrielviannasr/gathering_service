-- Loser Pot
SELECT
    e.id AS id_event,
    COUNT(r.id) AS rounds,
    SUM(r.loser_pot) AS loser_pot
FROM
    gathering.round r
    INNER JOIN gathering.event e ON e.id = r.id_event
WHERE
    r.canceled = false
    AND id_event = :idEvent
GROUP BY
    e.id;

-- Loser Pot using view
SELECT
    id_event,
    rounds,
    loser_pot
FROM
    gathering.vw_event_loser_pot
WHERE
    id_event = :idEvent;
