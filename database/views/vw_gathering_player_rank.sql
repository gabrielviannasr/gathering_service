CREATE OR REPLACE VIEW gathering.vw_gathering_player_rank AS
SELECT
	id_gathering,
	RANK() OVER (
-- PARTITION BY b.id_gathering garante que o ranking é calculado dentro de cada gathering individualmente.
-- (Sem isso, se houver várias gatherings, o RANK() pode comparar jogadores entre gatherings diferentes.)		
        PARTITION BY id_gathering
        ORDER BY COALESCE(SUM(positive - negative), 0) DESC,
                 COALESCE(SUM(rounds), 0) ASC
	) AS rank,
	id_player,
    player_name,
    COUNT(id_event) AS total, 
    COALESCE(SUM(wins), 0) AS wins,
    COALESCE(SUM(rounds), 0) AS rounds,
    COALESCE(SUM(positive), 0) AS positive,
    COALESCE(SUM(negative), 0) AS negative,
    COALESCE(SUM(positive - negative), 0) AS rank_balance
FROM
    gathering.vw_event_player_balance
GROUP BY
	id_gathering, id_player, player_name
ORDER BY
    id_gathering, rank, player_name;

-- Versão com WITH, considerando que a view base (player balance) não calcula rank balance.
CREATE OR REPLACE VIEW gathering.vw_gathering_player_rank AS
-- O PostgreSQL calcula os agregados uma vez, e o RANK() só lê o resultado já resumido.
WITH balance AS (
    SELECT
        id_gathering,
        id_player,
        player_name,
        COUNT(id_event) AS total_events,
        COALESCE(SUM(wins), 0) AS wins,
        COALESCE(SUM(rounds), 0) AS rounds,
        COALESCE(SUM(positive), 0) AS positive,
        COALESCE(SUM(negative), 0) AS negative,
        COALESCE(SUM(positive - negative), 0) AS rank_balance
    FROM gathering.vw_event_player_balance
    GROUP BY id_gathering, id_player, player_name
)
SELECT
    id_gathering,
    RANK() OVER (
        -- PARTITION BY b.id_gathering garante que o ranking é calculado dentro de cada gathering individualmente.
        -- (Sem isso, se houver várias gatherings, o RANK() pode comparar jogadores entre gatherings diferentes.)
        PARTITION BY id_gathering
        ORDER BY rank_balance DESC, rounds ASC
    ) AS rank,
    id_player,
    player_name,
    total_events,
    wins,
    rounds,
    positive,
    negative,
    rank_balance
FROM
	balance
ORDER BY
	id_gathering, rank, player_name;

COMMENT ON VIEW gathering.vw_gathering_player_rank IS
'Provides the cumulative player ranking within each gathering, based on total balance and overall performance across all events.';

-- Versão sem WITH usando a view base (vw_gathering_player_balance)
CREATE OR REPLACE VIEW gathering.vw_gathering_player_rank AS
SELECT
    id_gathering,
    RANK() OVER (
        -- PARTITION BY id_gathering garante que o ranking é calculado dentro de cada gathering individualmente.
        PARTITION BY id_gathering
        ORDER BY rank_balance DESC, rounds ASC
    ) AS rank,
    id_player,
    player_name,
    events,
    wins,
    rounds,
    positive,
    negative,
    rank_balance
FROM
    gathering.vw_gathering_player_balance
ORDER BY
    id_gathering, rank, player_name;

COMMENT ON VIEW gathering.vw_gathering_player_rank IS
'Provides the player ranking within each gathering based on cumulative performance and balance, built upon vw_gathering_player_balance.';