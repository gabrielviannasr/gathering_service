CREATE OR REPLACE VIEW gathering.vw_gathering_confra_pot AS
    SELECT
        g.id AS id_gathering,
        g.name AS gathering_name,
        COUNT(e.id) AS events,
        COALESCE(SUM(e.rounds), 0) AS rounds,
        COALESCE(SUM(e.confra_pot), 0) AS confra_pot
    FROM
        gathering.gathering g
        INNER JOIN gathering.event e ON e.id_gathering = g.id
    GROUP BY
        g.id, g.name
    ORDER BY
        g.name;

COMMENT ON VIEW gathering.vw_gathering_confra_pot IS
'Exibe o total acumulado do pote da confra, o número total de rodadas e a quantidade de eventos em cada confra.';