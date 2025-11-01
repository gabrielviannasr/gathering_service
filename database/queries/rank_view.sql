-- Rank
SELECT
    RANK() OVER (ORDER BY (positive - negative) DESC, rounds ASC) AS rank,
    id_player,
    name,
    wins,
    rounds,
    positive,
    -negative AS negative,
    positive - negative AS rank_balance
FROM gathering.vw_event_player_balance
WHERE
	id_event = :idEvent
ORDER BY
    rank, name;
