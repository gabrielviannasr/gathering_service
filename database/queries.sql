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
        p.id as id_player,
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
    p.id as id_player,
    p.username,
    COUNT(CASE WHEN rp.rank = 1 THEN 1 END) AS wins,
    COUNT(rp.id_player) AS rounds,
    COUNT(CASE WHEN rp.rank = 1 THEN 1 END) * e.registration_fee * 4 AS positive,
    COUNT(rp.id_player) * e.registration_fee AS negative,
    SUM (CASE WHEN rp.rank = 1 THEN r.boosters_prize ELSE 0 END) AS boosters_prize
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
    boosters_prize
FROM (
    SELECT
        p.id as id_player,
        p.username,
        COUNT(CASE WHEN rp.rank = 1 THEN 1 END) AS wins,
        COUNT(rp.id_player) AS rounds,
        COUNT(CASE WHEN rp.rank = 1 THEN 1 END) * e.registration_fee * 4 AS positive,
        COUNT(rp.id_player) * e.registration_fee AS negative,
        SUM (CASE WHEN rp.rank = 1 THEN r.boosters_prize ELSE 0 END) AS boosters_prize
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
    -negative as negative,
    positive - negative AS balance,
    -prize as prize,
    positive - negative - prize as wallet
FROM (
    SELECT
        p.id as id_player,
        p.username,
        COUNT(CASE WHEN rp.rank = 1 THEN 1 END) AS wins,
        COUNT(rp.id_player) AS rounds,
        COUNT(CASE WHEN rp.rank = 1 THEN 1 END) * e.registration_fee * 4 AS positive,
        COUNT(rp.id_player) * e.registration_fee AS negative,
        SUM (CASE WHEN rp.rank = 1 THEN r.boosters_prize ELSE 0 END) AS prize
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
