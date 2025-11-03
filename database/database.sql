/* CREATE SEQUENCES */
CREATE SEQUENCE gathering.sequence_event START 1;
CREATE SEQUENCE gathering.sequence_format START 1;
CREATE SEQUENCE gathering.sequence_gathering START 1;
CREATE SEQUENCE gathering.sequence_player START 1;
CREATE SEQUENCE gathering.sequence_transaction START 1;
CREATE SEQUENCE gathering.sequence_rank START 1;
CREATE SEQUENCE gathering.sequence_round START 1;
CREATE SEQUENCE gathering.sequence_score START 1;
CREATE SEQUENCE gathering.sequence_transaction_type START 1;
/* CREATE SEQUENCES */

/* CREATE TABLES */
CREATE TABLE gathering.player (
    id INT DEFAULT nextval('gathering.sequence_player'::regclass) PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    username VARCHAR(20) UNIQUE,
    email VARCHAR(50) UNIQUE,
    password VARCHAR(20),
    wallet NUMERIC NOT NULL DEFAULT 0
);

CREATE TABLE gathering.gathering (
    id INT DEFAULT nextval('gathering.sequence_gathering'::regclass) PRIMARY KEY,
	id_player INT NOT NULL, -- createdBy and the person in charge of the event
	year INT DEFAULT EXTRACT(YEAR FROM CURRENT_DATE),
    name VARCHAR(20),
	CONSTRAINT fk_gathering_player FOREIGN KEY (id_player) REFERENCES gathering.player(id)
);

CREATE TABLE gathering.transaction_type (
    id INT DEFAULT nextval('gathering.sequence_transaction_type'::regclass) PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE gathering.transaction (
    id INT DEFAULT nextval('gathering.sequence_transaction'::regclass) PRIMARY KEY,
    id_player INT NOT NULL,
    id_gathering INT NOT NULL,
    id_transaction_type INT NOT NULL,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    amount NUMERIC(10,2) NOT NULL,
	description VARCHAR(500),
    CONSTRAINT fk_transaction_player FOREIGN KEY (id_player) REFERENCES gathering.player(id),
    CONSTRAINT fk_transaction_gathering FOREIGN KEY (id_gathering) REFERENCES gathering.gathering(id),
    CONSTRAINT fk_transaction_transaction_type FOREIGN KEY (id_transaction_type) REFERENCES gathering.transaction_type(id)
);

CREATE TABLE gathering.format (
    id INT DEFAULT nextval('gathering.sequence_format'::regclass) PRIMARY KEY,
    name VARCHAR(20) NOT NULL,
    life_count INT NOT NULL
);

CREATE TABLE gathering.event (
    id INT DEFAULT nextval('gathering.sequence_event'::regclass) PRIMARY KEY,
    id_gathering INT NOT NULL,
    id_format INT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    players INT NOT NULL DEFAULT 0,
    rounds INT NOT NULL DEFAULT 0,
    confra_fee NUMERIC(10,2) NOT NULL DEFAULT 0 CHECK (loser_fee4 >= 0),
    round_fee NUMERIC(10,2) NOT NULL DEFAULT 0 CHECK (loser_fee4 >= 0),
    loser_fee4 NUMERIC(10,2) NOT NULL DEFAULT 0 CHECK (loser_fee4 >= 0),
    loser_fee5 NUMERIC(10,2) NOT NULL DEFAULT 0 CHECK (loser_fee5 >= 0),
    loser_fee6 NUMERIC(10,2) NOT NULL DEFAULT 0 CHECK (loser_fee6 >= 0),    
    loser_pot NUMERIC(10,2) NOT NULL DEFAULT 0 CHECK (loser_pot >= 0),
    confra_pot NUMERIC(10,2) NOT NULL DEFAULT 0 CHECK (confra_pot >= 0),
	CONSTRAINT fk_event_gathering FOREIGN KEY (id_gathering) REFERENCES gathering.gathering(id),
    CONSTRAINT fk_event_format FOREIGN KEY (id_format) REFERENCES gathering.format(id)
);

CREATE TABLE gathering.round (
    id INT DEFAULT nextval('gathering.sequence_round'::regclass) PRIMARY KEY,
    id_event INT NOT NULL,
    id_format INT NOT NULL,
    id_player_winner INT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    round INT NOT NULL,
    players INT NOT NULL DEFAULT 0 CHECK (players >= 0),
    prize NUMERIC(10,2) NOT NULL DEFAULT 0 CHECK (prize >= 0),
    loser_pot NUMERIC(10,2) NOT NULL DEFAULT 0 CHECK (loser_pot >= 0),
    canceled BOOLEAN NOT NULL DEFAULT false,
    CONSTRAINT fk_round_event FOREIGN KEY (id_event) REFERENCES gathering.event(id),
    CONSTRAINT fk_round_format FOREIGN KEY (id_format) REFERENCES gathering.format(id),
    CONSTRAINT fk_round_player_winner FOREIGN KEY (id_player_winner) REFERENCES gathering.player(id)
);

-- Score (Round_Player)
CREATE TABLE gathering.score (
    id INT DEFAULT nextval('gathering.sequence_score'::regclass) PRIMARY KEY,
    id_round INT NOT NULL,
    id_player INT NOT NULL,
    id_player_killed_by INT,
    is_dead BOOLEAN NOT NULL DEFAULT false,
    primary_commander_name VARCHAR,
    primary_commander_count INT NOT NULL DEFAULT 0,
    secondary_commander_name VARCHAR,
    secondary_commander_count INT,
    infect_count INT NOT NULL DEFAULT 0,
    life_count INT NOT NULL DEFAULT 0,
    CONSTRAINT fk_score_round FOREIGN KEY (id_round) REFERENCES gathering.round(id),
    CONSTRAINT fk_score_player FOREIGN KEY (id_player) REFERENCES gathering.player(id),
    CONSTRAINT fk_score_player_killed_by FOREIGN KEY (id_player_killed_by) REFERENCES gathering.player(id)
);

-- Rank (Event_Player)
CREATE TABLE gathering.rank (
    id INT DEFAULT nextval('gathering.sequence_rank'::regclass) PRIMARY KEY,
    id_event INT NOT NULL,
	id_player INT NOT NULL,
	rank INT,
    wins INT NOT NULL DEFAULT 0,
    rounds INT NOT NULL DEFAULT 0,
	positive NUMERIC(10,2) NOT NULL DEFAULT 0,
	negative NUMERIC(10,2) NOT NULL DEFAULT 0,
    rank_balance NUMERIC(10,2) NOT NULL DEFAULT 0,
    loser_pot NUMERIC(10,2) NOT NULL DEFAULT 0,
    final_balance NUMERIC(10,2) NOT NULL DEFAULT 0,
    CONSTRAINT fk_rank_event FOREIGN KEY (id_event) REFERENCES gathering.event(id),
    CONSTRAINT fk_rank_player FOREIGN KEY (id_player) REFERENCES gathering.player(id)
);
/* CREATE TABLES */

/* CREATE VIEWS */
CREATE OR REPLACE VIEW gathering.vw_event_confra_pot AS
SELECT
    e.id AS id_event,
    COUNT(DISTINCT s.id_player) AS players,
    COUNT(DISTINCT s.id_player) * e.confra_fee AS confra_pot
FROM
    gathering.score s
    INNER JOIN gathering.round r ON r.id = s.id_round
    INNER JOIN gathering.player p ON p.id = s.id_player
    INNER JOIN gathering.event e ON e.id = r.id_event
WHERE
    r.canceled = false
GROUP BY
    e.id;

COMMENT ON VIEW gathering.vw_event_confra_pot IS
'Displays the total number of players and the total confra pot amount for each event.';

CREATE OR REPLACE VIEW gathering.vw_event_loser_pot AS
SELECT
	e.id AS id_event,
    COUNT(r.id) AS rounds,
    SUM(r.loser_pot) AS loser_pot
FROM
    gathering.round r
    INNER JOIN gathering.event e ON e.id = r.id_event
WHERE
    r.canceled = false
GROUP BY
    e.id;

COMMENT ON VIEW gathering.vw_event_loser_pot IS
'Displays the total number of rounds and the accumulated loser pot per event.';

CREATE OR REPLACE VIEW gathering.vw_event_player_balance AS
SELECT
    e.id AS id_event,
    p.id AS id_player,
    p.name,
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
    e.id, p.id;

COMMENT ON VIEW gathering.vw_event_player_balance IS
'Provides the balance of each player per event.
Used as the base view for event rank calculations.';

CREATE OR REPLACE VIEW gathering.vw_event_player_rank AS
SELECT
    id_event,
	RANK() OVER (ORDER BY (positive - negative) DESC, rounds ASC) AS rank,
    id_player,
    name,
    wins,
    rounds,
    positive,
    negative,
    positive - negative AS rank_balance
FROM
    gathering.vw_event_player_balance
ORDER BY
    rank, name;

COMMENT ON VIEW gathering.vw_event_player_rank IS
'Provides the player ranking for each event based on balance and performance.';

CREATE OR REPLACE VIEW gathering.vw_event_rank_count AS
SELECT
	id_event,
	rank,
    COUNT(*)
FROM
    gathering.vw_event_player_rank
GROUP BY
    id_event, rank
ORDER BY
    rank DESC;

COMMENT ON VIEW gathering.vw_event_rank_count IS
'Indicates how many players share each rank, used to distribute the loser pot.';

CREATE OR REPLACE VIEW gathering.vw_gathering_confra_total AS
SELECT
    g.id AS id_gathering,
    g.name,
    SUM(e.confra_pot) AS total_confra_pot
FROM
    gathering.event e
    INNER JOIN gathering.gathering g ON g.id = e.id_gathering
GROUP BY
    g.id
ORDER BY
    g.name;

COMMENT ON VIEW gathering.vw_gathering_confra_total IS
'Displays the total accumulated confra pot for each gathering, based on all related events.';

-- NOT USED YET
CREATE OR REPLACE VIEW gathering.vw_player_balance AS
SELECT
    p.id AS player_id,
    p.name AS player_name,
    SUM(
        CASE 
            WHEN t.transaction_type_id = 1 THEN t.amount -- Balance
            WHEN t.transaction_type_id = 2 THEN -t.amount -- Payment
            WHEN t.transaction_type_id = 3 THEN t.amount -- Withdrawal
            ELSE 0
        END
    ) AS balance
FROM gathering.transaction t
JOIN gathering.player p ON p.id = t.player_id
GROUP BY p.id, p.name;
/* CREATE VIEWS */

-- Create function to update wallet and return player
DROP FUNCTION IF EXISTS gathering.update_wallet;
CREATE OR REPLACE FUNCTION gathering.update_wallet(id_player_param BIGINT)
RETURNS SETOF gathering.player AS $$
DECLARE
    updated_player gathering.player%ROWTYPE;
BEGIN
    UPDATE gathering.player
    SET wallet = (
        COALESCE(
            (SELECT SUM(transaction.amount)
             FROM gathering.transaction
             WHERE 
             	transaction.id_player = id_player_param),
            0
        ) +
        COALESCE(
            (SELECT SUM(rank.final_balance)
             FROM gathering.rank
             WHERE rank.id_player = id_player_param),
            0
        )
    )
    WHERE id = id_player_param
    RETURNING * INTO updated_player;

    RETURN NEXT updated_player;
    RETURN;
END;
$$ LANGUAGE plpgsql;

-- Create indexes
-- CREATE INDEX IF NOT EXISTS idx_gathering_format_name ON gathering.format(name);
-- CREATE INDEX IF NOT EXISTS idx_gathering_player_email ON gathering.player(email);
-- CREATE INDEX IF NOT EXISTS idx_gathering_player_name ON gathering.player(name);
-- CREATE INDEX IF NOT EXISTS idx_gathering_player_username ON gathering.player(username);
CREATE INDEX idx_rank_event_player ON gathering.rank(event_id, player_id);
