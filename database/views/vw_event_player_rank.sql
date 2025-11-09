-- Versão 2. Utiliza a view base (vw_event_player_balance).
CREATE OR REPLACE VIEW gathering.vw_event_player_rank AS
    SELECT
        id_gathering,
        gathering_name,
        id_event,
        RANK() OVER (
            -- PARTITION BY id_event garante que o ranking é calculado dentro de cada evento individualmente.
            PARTITION BY id_event
            ORDER BY rank_balance DESC, rounds ASC
        ) AS rank,
        id_player,
        player_name,
        wins,
        rounds,
        positive,
        negative,
        rank_balance
    FROM
        gathering.vw_event_player_balance
    ORDER BY
        id_event, rank, player_name;

COMMENT ON VIEW gathering.vw_event_player_rank IS
'Apresenta o ranking dos jogadores em cada evento, calculado com base no saldo (rank balance).';

-- Versão 1. Com WITH, considerando que a view base (player balance) não calcula rank balance.
CREATE OR REPLACE VIEW gathering.vw_event_player_rank AS
WITH player_balance AS (
    SELECT
        id_gathering,
        id_event,
        id_player,
        player_name,
        wins,
        rounds,
        positive,
        negative,
        (positive - negative) AS rank_balance
    FROM
        gathering.vw_event_player_balance
)
SELECT
    id_gathering,
    id_event,
    RANK() OVER (
        -- PARTITION BY id_event garante que o ranking é calculado dentro de cada evento individualmente.
        -- (Sem isso, se houver vários eventos, o RANK() pode comparar jogadores entre eventos diferentes.)
        PARTITION BY id_event
        ORDER BY rank_balance DESC, rounds ASC
    ) AS rank,
    id_player,
    player_name,
    wins,
    rounds,
    positive,
    negative,
    rank_balance
FROM
    player_balance
ORDER BY
    id_event, rank, player_name;