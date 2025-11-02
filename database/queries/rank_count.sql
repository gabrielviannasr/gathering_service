-- Get rank count to determine how many players will share the loserPot
SELECT
    rank,
    COUNT(*)
FROM
    gathering.vw_event_rank
WHERE
    id_event = :idEvent
GROUP BY
    rank
ORDER BY
    rank DESC;

SELECT
    id_event,
    rank,
    count
FROM
    gathering.vw_event_loser_pot_rank_distribution
WHERE
    id_event = :idEvent;