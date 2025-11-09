-- Versão 2. Otimizada com WITH.
CREATE OR REPLACE VIEW gathering.vw_event_player_balance AS
    WITH player_balance AS (
        SELECT
            e.id_gathering,
            g.name AS gathering_name,
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
            INNER JOIN gathering.gathering g ON g.id = e.id_gathering
        WHERE
            r.canceled = false
        GROUP BY
    --	    e.id, p.id
    --		Garante portabilidade e evita warning em versões futuras.
            g.id, e.id, p.id, p.name, e.round_fee
    )
    SELECT
        id_gathering,
        gathering_name
        id_event,
        id_player,
        player_name,
        wins,
        rounds,
        positive,
        negative,
        positive - negative AS rank_balance
    FROM
        player_balance
    ORDER BY
        player_name;

COMMENT ON VIEW gathering.vw_event_player_balance IS
'Exibe o desempenho e o saldo de cada jogador em um evento,
incluindo número de vitórias, rodadas, premiação, taxas e saldo resultante (rank balance).';

-- Versão 1. Sem rank_balance.
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