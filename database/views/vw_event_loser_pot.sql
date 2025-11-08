CREATE OR REPLACE VIEW gathering.vw_event_loser_pot AS
SELECT
    g.id AS id_gathering,
    g.name AS gathering_name,
    e.id AS id_event,
    COUNT(r.id) AS rounds,
    SUM(r.loser_pot) AS loser_pot,
    SUM(r.prize) AS prize
FROM
    gathering.round r
    INNER JOIN gathering.event e ON e.id = r.id_event
    INNER JOIN gathering.gathering g ON g.id = e.id_gathering
WHERE
    r.canceled = false
GROUP BY
    g.id, e.id;

COMMENT ON VIEW gathering.vw_event_loser_pot IS
'Displays the total number of rounds, the total prize awarded and the accumulated loser pot per event.';