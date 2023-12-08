-- Select rank v1
SELECT
	RANK() OVER (ORDER BY COUNT(CASE WHEN rp.rank = 1 THEN 1 END) DESC) AS rank,
    p.id AS id_player,
	p.username,
    COUNT(CASE WHEN rp.rank = 1 THEN 1 END) AS wins,
    COUNT(rp.id_player) AS rounds

FROM gathering.round_player rp
    INNER JOIN gathering.round r ON r.id = rp.id_round
    INNER JOIN gathering.event e ON e.id = r.id_event
    INNER JOIN gathering.player p ON p.id = rp.id_player

WHERE
    e.id = 2
    AND r.canceled = false
GROUP BY
    p.id, p.username
ORDER BY
    rank, p.username;
	
-- Select rank v2
SELECT
	RANK() OVER (ORDER BY COUNT(CASE WHEN rp.rank = 1 THEN 1 END) DESC, COUNT(rp.id_player)) AS rank,
    p.id AS id_player,
	p.username,
    COUNT(CASE WHEN rp.rank = 1 THEN 1 END) AS wins,
    COUNT(rp.id_player) AS rounds,
    COUNT(CASE WHEN rp.rank = 1 THEN 1 END) * e.registration_fee * 4 AS positive,
    COUNT(rp.id_player) * e.registration_fee AS negative

FROM gathering.round_player rp
    INNER JOIN gathering.round r ON r.id = rp.id_round
    INNER JOIN gathering.event e ON e.id = r.id_event
    INNER JOIN gathering.player p ON p.id = rp.id_player

WHERE
    e.id = 2
    AND r.canceled = false
GROUP BY
    p.id, p.username, e.registration_fee
ORDER BY
    rank, p.username;
	
-- Select rank v3
SELECT
    RANK() OVER (ORDER BY wins DESC, rounds) AS rank,
    id_player,
    username,
    wins,
    rounds,
    positive,
    negative,
    positive - negative AS balance
FROM (
    SELECT
        p.id AS id_player,
        p.username,
        COUNT(CASE WHEN rp.rank = 1 THEN 1 END) AS wins,
        COUNT(rp.id_player) AS rounds,
        COUNT(CASE WHEN rp.rank = 1 THEN 1 END) * e.registration_fee * 4 AS positive,
        COUNT(rp.id_player) * e.registration_fee AS negative
    FROM
        gathering.round_player rp
        INNER JOIN gathering.round r ON r.id = rp.id_round
        INNER JOIN gathering.event e ON e.id = r.id_event
        INNER JOIN gathering.player p ON p.id = rp.id_player
    WHERE
        e.id = 2
        AND r.canceled = false
    GROUP BY
        p.id, p.username, e.registration_fee
) AS subquery
ORDER BY
    rank, username;

-- Select rank v4
SELECT
    p.id AS id_player,
    p.username,
    COUNT(CASE WHEN rp.rank = 1 THEN 1 END) AS wins,
    COUNT(rp.id_player) AS rounds,
    COUNT(CASE WHEN rp.rank = 1 THEN 1 END) * e.registration_fee * 4 AS positive,
    COUNT(rp.id_player) * e.registration_fee AS negative,
    SUM (CASE WHEN rp.rank = 1 THEN r.prize_taken ELSE 0 END) AS prize_taken
FROM
    gathering.round_player rp
    INNER JOIN gathering.round r ON r.id = rp.id_round
    INNER JOIN gathering.event e ON e.id = r.id_event
    INNER JOIN gathering.player p ON p.id = rp.id_player
WHERE
    e.id = 2
    AND r.canceled = false
GROUP BY
    p.id, p.username, e.registration_fee;
	
-- Select rank v5
SELECT
    RANK() OVER (ORDER BY wins DESC, rounds) AS rank,
    id_player,
    username,
    wins,
    rounds,
    positive,
    negative,
    positive - negative AS balance,
    prize_taken
FROM (
    SELECT
        p.id AS id_player,
        p.username,
        COUNT(CASE WHEN rp.rank = 1 THEN 1 END) AS wins,
        COUNT(rp.id_player) AS rounds,
        COUNT(CASE WHEN rp.rank = 1 THEN 1 END) * e.registration_fee * 4 AS positive,
        COUNT(rp.id_player) * e.registration_fee AS negative,
        SUM (CASE WHEN rp.rank = 1 THEN r.prize_taken ELSE 0 END) AS prize_taken
    FROM
        gathering.round_player rp
        INNER JOIN gathering.round r ON r.id = rp.id_round
        INNER JOIN gathering.event e ON e.id = r.id_event
        INNER JOIN gathering.player p ON p.id = rp.id_player
    WHERE
        e.id = 2
        AND r.canceled = false
    GROUP BY
        p.id, p.username, e.registration_fee
) AS subquery
ORDER BY
    rank, username;

-- Select rank v6
SELECT
    RANK() OVER (ORDER BY wins DESC, rounds) AS rank,
    id_player,
    username,
    wins,
    rounds,
    positive,
    -negative AS negative,
    positive - negative AS rank_balance,
    -prize_taken AS prize_taken,
    positive - negative - prize_taken AS final_balance
FROM (
    SELECT
        p.id AS id_player,
        p.username,
        COUNT(CASE WHEN rp.rank = 1 THEN 1 END) AS wins,
        COUNT(rp.id_player) AS rounds,
        COUNT(CASE WHEN rp.rank = 1 THEN 1 END) * e.registration_fee * 4 AS positive,
        COUNT(rp.id_player) * e.registration_fee AS negative,
        SUM (CASE WHEN rp.rank = 1 THEN r.prize_taken ELSE 0 END) AS prize_taken
    FROM
        gathering.round_player rp
        INNER JOIN gathering.round r ON r.id = rp.id_round
        INNER JOIN gathering.event e ON e.id = r.id_event
        INNER JOIN gathering.player p ON p.id = rp.id_player
    WHERE
        e.id = :idEvent
        AND r.canceled = false
    GROUP BY
        p.id, p.username, e.registration_fee
) AS subquery
ORDER BY
    rank, username;

-- Select confra_pot and loser_pot
SELECT 
	count5 * confra_fee5 + count6 * confra_fee6 AS confraPot,
	count5 * loser_fee5 + count6 * loser_fee6 AS loserPot

FROM (
	SELECT 
		COUNT(CASE WHEN r.players = 5 THEN 1 END) AS count5,
		COUNT(CASE WHEN r.players = 6 THEN 1 END) AS count6,
		e.confra_fee5,
		e.confra_fee6,
		e.loser_fee5,
		e.loser_fee6
		
	FROM gathering.round r
		INNER JOIN gathering.event e ON e.id = r.id_event
	WHERE
		r.id_event = 2
		AND r.canceled = false
	GROUP BY e.confra_fee5, e.confra_fee6, loser_fee5, loser_fee6
) AS subquery;

-- Select rank count to determine how many players will share the loserPot
SELECT rank, COUNT(*) FROM (
    SELECT
        RANK() OVER (ORDER BY wins DESC, rounds) AS rank,
        id_player,
        username,
        wins,
        rounds,
        positive,
        -negative AS negative,
        positive - negative AS rank_balance,
        -prize_taken AS prize_taken,
        positive - negative - prize_taken AS final_balance
	FROM (
        SELECT
            p.id AS id_player,
            p.username,
            COUNT(CASE WHEN rp.rank = 1 THEN 1 END) AS wins,
            COUNT(rp.id_player) AS rounds,
            COUNT(CASE WHEN rp.rank = 1 THEN 1 END) * e.registration_fee * 4 AS positive,
            COUNT(rp.id_player) * e.registration_fee AS negative,
            SUM (CASE WHEN rp.rank = 1 THEN r.prize_taken ELSE 0 END) AS prize_taken
        FROM
            gathering.round_player rp
            INNER JOIN gathering.round r ON r.id = rp.id_round
            INNER JOIN gathering.event e ON e.id = r.id_event
            INNER JOIN gathering.player p ON p.id = rp.id_player
        WHERE
            e.id = :idEvent
            AND r.canceled = false
        GROUP BY
            p.id, p.username, e.registration_fee
	) AS subquery
	ORDER BY
        rank, username
) AS rank_query
GROUP BY rank
ORDER BY rank DESC;
