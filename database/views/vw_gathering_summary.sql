-- Versão 2. Evita inconsistências. Dados sempre calculados.
CREATE OR REPLACE VIEW gathering.vw_gathering_summary AS
SELECT
	id_gathering,
	gathering_name,
	COUNT(id_event) AS events,
	COALESCE(SUM(players), 0) AS players,
	COALESCE(SUM(rounds), 0) AS rounds,
	COALESCE(SUM(loser_pot), 0) AS loser_pot,
	COALESCE(SUM(confra_pot), 0) AS confra_pot,
	COALESCE(SUM(prize), 0) AS prize
FROM
	gathering.vw_event_summary
GROUP BY
    id_gathering, gathering_name
ORDER BY
    gathering_name;

COMMENT ON VIEW gathering.vw_gathering_summary IS
'Apresenta um resumo consolidado de cada confra (gathering),
incluindo a quantidade total de eventos, jogadores, rodadas,
e os valores acumulados dos potes (dos derrotados e da confra),
além do total de premiações.

Os dados são derivados das views de evento, garantindo consistência
e cálculos sempre atualizados.';

-- Versão 1. Sujeita a inconsistências, pois utiliza a tabela de eventos (event).
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