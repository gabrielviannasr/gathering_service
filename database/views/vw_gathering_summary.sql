CREATE OR REPLACE VIEW gathering.vw_gathering_summary AS
SELECT
    g.id AS id_gathering,
    g.name AS gathering_name,
    COUNT(e.id) AS events,
    COALESCE(SUM(e.rounds), 0) AS rounds,
    COALESCE(SUM(e.loser_pot), 0) AS loser_pot,
    COALESCE(SUM(e.confra_pot), 0) AS confra_pot,
    COALESCE(SUM(e.prize), 0) AS prize
FROM
    gathering.gathering g
    INNER JOIN gathering.event e ON e.id_gathering = g.id
GROUP BY
    g.id, g.name
ORDER BY
    g.name;

COMMENT ON VIEW gathering.vw_gathering_summary IS
'Resumo consolidado das conferências (gatherings).

Apresenta a quantidade total de eventos, rodadas, valores acumulados
de loser pot, confra pot e premiação (prize) de cada gathering.

Os dados são obtidos diretamente da tabela de eventos (event),
portanto dependem da consistência dos valores consolidados nela.';