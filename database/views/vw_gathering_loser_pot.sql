CREATE OR REPLACE VIEW gathering.vw_gathering_loser_pot AS
SELECT
    g.id AS id_gathering,
    g.name AS gathering_name,
    COALESCE(SUM(r.prize), 0) AS prize,
    COALESCE(SUM(r.loser_pot), 0) AS loser_pot
FROM
    gathering.gathering g
    INNER JOIN gathering.event e ON e.id_gathering = g.id
    INNER JOIN gathering.round r ON r.id_event = e.id
WHERE
    r.canceled = false
GROUP BY
    g.id
ORDER BY
    g.name;

COMMENT ON VIEW gathering.vw_gathering_loser_pot IS
'Summarizes the total prizes and loser pots accumulated across all events within each gathering.';