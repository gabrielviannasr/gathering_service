CREATE OR REPLACE VIEW gathering.vw_event_confra_pot AS
SELECT
    e.id_gathering,
    g.name AS gathering_name,
    e.id AS id_event,
    COUNT(DISTINCT s.id_player) AS players,
    COUNT(DISTINCT s.id_player) * e.confra_fee AS confra_pot
FROM
    gathering.score s
    INNER JOIN gathering.round r ON r.id = s.id_round
    INNER JOIN gathering.player p ON p.id = s.id_player
    INNER JOIN gathering.event e ON e.id = r.id_event
    INNER JOIN gathering.gathering g ON g.id = e.id_gathering
WHERE
    r.canceled = false
GROUP BY
    g.id, g.name, e.id;

COMMENT ON VIEW gathering.vw_event_confra_pot IS
'Exibe o total de jogadores e o valor acumulado destinado ao pote da confra em cada evento.';