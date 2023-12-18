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
    COUNT(CASE WHEN rp.rank = 1 THEN 1 END) * e.prize AS positive,
    COUNT(rp.id_player) * e.registration_fee AS negative
FROM gathering.round_player rp
    INNER JOIN gathering.round r ON r.id = rp.id_round
    INNER JOIN gathering.event e ON e.id = r.id_event
    INNER JOIN gathering.player p ON p.id = rp.id_player
WHERE
    e.id = 2
    AND r.canceled = false
GROUP BY
    p.id, p.username, e.registration_fee, e.prize
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
        COUNT(CASE WHEN rp.rank = 1 THEN 1 END) * e.prize AS positive,
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
        p.id, p.username, e.registration_fee, e.prize
) AS subquery
ORDER BY
    rank, username;

-- Select rank v4
SELECT
    p.id AS id_player,
    p.username,
    COUNT(CASE WHEN rp.rank = 1 THEN 1 END) AS wins,
    COUNT(rp.id_player) AS rounds,
    COUNT(CASE WHEN rp.rank = 1 THEN 1 END) * e.prize AS positive,
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
    p.id, p.username, e.registration_fee, e.prize;
	
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
        COUNT(CASE WHEN rp.rank = 1 THEN 1 END) * e.prize AS positive,
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
        p.id, p.username, e.registration_fee, e.prize
) AS subquery
ORDER BY
    rank, username;

-- Rank
SELECT
    RANK() OVER (ORDER BY (positive - negative) DESC, rounds ASC) AS rank,
    id_player AS idPlayer,
    username,
    wins,
    rounds,
    positive,
    -negative AS negative,
    positive - negative AS rankBalance,
    -prize_taken AS prizeTaken,
    positive - negative - prize_taken AS finalBalance
FROM (
    SELECT
        p.id AS id_player,
        p.username,
        COUNT(CASE WHEN rp.rank = 1 THEN 1 END) AS wins,
        COUNT(rp.id_player) AS rounds,
        COUNT(CASE WHEN rp.rank = 1 THEN 1 END) * e.prize AS positive,
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
        p.id, p.username, e.registration_fee, e.prize
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

-- RankCountProjection: Select rank count to determine how many players will share the loserPot
SELECT rank, COUNT(*) FROM (
    SELECT
        RANK() OVER (ORDER BY (positive - negative) DESC, rounds DESC) AS rank,
        id_player,
		name,
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
			p.name,
            p.username,
            COUNT(CASE WHEN rp.rank = 1 THEN 1 END) AS wins,
            COUNT(rp.id_player) AS rounds,
            COUNT(CASE WHEN rp.rank = 1 THEN 1 END) * e.prize AS positive,
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
            p.id, p.username, e.registration_fee, e.prize
	) AS subquery
	ORDER BY
        rank, name, username
) AS rank_query
GROUP BY rank
ORDER BY rank DESC;

-- PlayerRoundProjection: Select total of players and roudns
SELECT
    COUNT(DISTINCT rp.id_player) AS players,
    COUNT(DISTINCT rp.id_round) AS rounds
FROM
    gathering.round_player rp
    JOIN gathering.round r ON r.id = rp.id_round
    JOIN gathering.event e ON e.id = r.id_event
WHERE
    e.id = :idEvent
    AND r.canceled = false;

-- Select wallet v1
SELECT
	id_player,
	username,
	invoice,
	final_balance,
	invoice + final_balance AS wallet
FROM (
	SELECT
		player.id AS id_player,
		player.username,
		SUM(DISTINCT payment.invoice IS NOT NULL THEN payment.invoice ELSE 0 END) AS invoice,
		SUM(DISTINCT CASE WHEN rank.final_balance IS NOT NULL THEN rank.final_balance ELSE 0 END) AS final_balance,
		player.wallet
	FROM
		gathering.player player
		FULL OUTER JOIN gathering.payment payment ON player.id = payment.id_player
		FULL OUTER JOIN gathering.rank rank ON player.id = rank.id_player
	--WHERE
	--	id_player = :idPlayer
	--	id_player IN :idPlayerList
	GROUP BY player.username, player.id
	ORDER BY player.username
) AS subquery

-- Select wallet v2
SELECT
	id_player,
	username,
	invoice,
	final_balance,
	invoice + final_balance AS wallet
FROM (
	SELECT
		player.id AS id_player,
		player.username,
		COALESCE(SUM(DISTINCT payment.invoice), 0) AS invoice,
		COALESCE(SUM(DISTINCT rank.final_balance), 0) AS final_balance,
		player.wallet
	FROM
		gathering.player player
		FULL OUTER JOIN gathering.payment payment ON player.id = payment.id_player
		FULL OUTER JOIN gathering.rank rank ON player.id = rank.id_player
	--WHERE
	--	id_player = :idPlayer
	--	id_player IN :idPlayerList
	GROUP BY player.username, player.id
	ORDER BY player.username
) AS subquery

-- DASHBOARD PART 1: SUM OF FINAL BALANCE BY GATHERING
SELECT 
	player.id AS id_player,
	player.username,
	COALESCE(SUM(rank.final_balance), 0) AS events_balance
FROM gathering.player player		
	INNER JOIN gathering.rank rank ON player.id = rank.id_player
	INNER JOIN gathering.event event ON event.id = rank.id_event
WHERE
 	event.id_gathering = 2
GROUP BY player.id, player.username
ORDER BY player.username;

-- DASHBOARD PART 2: SELECT SUM OF TRANSACTIONS BY GATHERING
SELECT 
	player.id AS id_player,
	player.username,
	COALESCE(SUM(CASE WHEN transaction.id_gathering = 2 THEN transaction.amount ELSE 0 END), 0) AS transactions_balance
FROM gathering.player player		
	FULL OUTER JOIN gathering.transaction transaction ON player.id = transaction.id_player
GROUP BY player.id, player.username
ORDER BY player.username;

-- DASHBOARD PART 3: events_balance AND transactions_balance
SELECT 
	player.id AS id_player,
	player.username,
	COALESCE(SUM(rank.final_balance), 0) AS events_balance,
	COALESCE(SUM(CASE WHEN transaction.id_gathering = 2 THEN transaction.amount END), 0) AS transactions_balance
FROM gathering.player player		
	INNER JOIN gathering.rank rank ON player.id = rank.id_player
	INNER JOIN gathering.event event ON event.id = rank.id_event
	FULL OUTER JOIN gathering.transaction transaction ON player.id = transaction.id_player
WHERE
 	event.id_gathering = 2
GROUP BY player.id, player.username
ORDER BY player.username;

-- DashboardProjection
SELECT
	id_player AS idPlayer,
	username,
	events_balance AS eventsBalance,
	transactions_balance AS transactionsBalance,
	events_balance + transactions_balance AS finalBalance
FROM (
	SELECT
		player.id AS id_player,
		player.username,
		COALESCE(SUM(rank.final_balance), 0) AS events_balance,
		COALESCE(SUM(CASE WHEN transaction.id_gathering = :idGathering THEN transaction.amount END), 0) AS transactions_balance
	FROM gathering.player player		
		INNER JOIN gathering.rank rank ON player.id = rank.id_player
		INNER JOIN gathering.event event ON event.id = rank.id_event
		FULL OUTER JOIN gathering.transaction transaction ON player.id = transaction.id_player
	WHERE
	 	event.id_gathering = :idGathering
	GROUP BY player.id, player.username
	ORDER BY player.username
) AS subquery;

-- RankProjection
SELECT
	RANK() OVER (ORDER BY rank_balance DESC, rounds ASC) AS rank,
	p.name,
	p.username,
	r.wins,
	r.rounds,
	r.positive,
	r.negative,
	r.rank_balance,
	r.loser_pot,
	r.final_balance
FROM gathering.player p, gathering.rank r
WHERE 
	r.id_player = p.id and r.id_event = :idEvent
ORDER BY rank, name, username;
