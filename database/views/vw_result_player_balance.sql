-- Versão 2. Com rank e divisão do loser_pot. Utiliza o WITH para centralizar as agregações.
CREATE OR REPLACE VIEW gathering.vw_result_player_balance AS
WITH player_final_balance AS(
	SELECT
		g.id AS id_gathering,
	    g.name AS gathering_name,
		p.id AS id_player,
		p.name AS player_name,
		--r.rank,
		COUNT(r.id) AS events,
		COALESCE(SUM(r.wins), 0) AS wins,
	 	COALESCE(SUM(r.rounds), 0) AS rounds,
	    COALESCE(SUM(r.positive), 0) AS positive,
	    COALESCE(SUM(r.negative), 0) AS negative,
	    COALESCE(SUM(r.rank_balance), 0) AS rank_balance,
	    COALESCE(SUM(r.loser_pot), 0) AS loser_pot,
		-- COALESCE(SUM(COUNT(r.id) * e.confra_fee) FILTER (WHERE r.id_player = p.id), 0) AS confra_pot,
	    COALESCE(SUM(r.final_balance), 0) AS final_balance
	FROM
		gathering.result r
		INNER JOIN gathering.event e ON e.id = r.id_event
		INNER JOIN gathering.gathering g ON g.id = e.id_gathering
		INNER JOIN gathering.player p ON p.id = r.id_player
	GROUP BY
		g.id, p.id
)
SELECT
	id_gathering,
	gathering_name,
	id_player,
	player_name,
	RANK() OVER (
        -- PARTITION BY id_event garante que o ranking é calculado dentro de cada evento individualmente.
	    PARTITION BY id_gathering
	    ORDER BY rank_balance DESC, rounds ASC
	) AS rank,
	events,
	wins,
	rounds,
	positive,
	negative,
	rank_balance,
	loser_pot,
	-- confra_pot,
	final_balance
FROM
	player_final_balance
ORDER BY
	gathering_name, player_name

-- Versão 1. Sem rank
CREATE OR REPLACE VIEW gathering.vw_result_player_balance AS
SELECT
	g.id AS id_gathering,
    g.name AS gathering_name,
	p.id AS id_player,
	p.name AS player_name,
	--r.rank,
	COUNT(r.id) AS events,
	COALESCE(SUM(r.wins), 0) AS wins,
 	COALESCE(SUM(r.rounds), 0) AS rounds,
    COALESCE(SUM(r.positive), 0) AS positive,
    COALESCE(SUM(r.negative), 0) AS negative,
    COALESCE(SUM(r.rank_balance), 0) AS rank_balance,
    COALESCE(SUM(r.loser_pot), 0) AS loser_pot,
    COALESCE(SUM(r.final_balance), 0) AS final_balance
FROM
	gathering.result r
	INNER JOIN gathering.event e ON e.id = r.id_event
	INNER JOIN gathering.gathering g ON g.id = e.id_gathering
	INNER JOIN gathering.player p ON p.id = r.id_player
GROUP BY
	g.id, p.id
--	g.id, rank, p.name	
ORDER BY
	g.name, p.name