-- Create sequences
CREATE SEQUENCE gathering.sequence_event START 1;
CREATE SEQUENCE gathering.sequence_format START 1;
CREATE SEQUENCE gathering.sequence_gathering START 1;
CREATE SEQUENCE gathering.sequence_player START 1;
CREATE SEQUENCE gathering.sequence_transaction START 1;
CREATE SEQUENCE gathering.sequence_rank START 1;
CREATE SEQUENCE gathering.sequence_round START 1;
CREATE SEQUENCE gathering.sequence_score START 1;
CREATE SEQUENCE gathering.sequence_transaction_type START 1;

-- Create player table
CREATE TABLE gathering.player (
    id INT DEFAULT nextval('gathering.sequence_player'::regclass) PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    username VARCHAR(20) UNIQUE,
    email VARCHAR(50) UNIQUE,
    password VARCHAR(20),
    wallet NUMERIC NOT NULL DEFAULT 0
);

-- Create gathering table
CREATE TABLE gathering.gathering (
    id INT DEFAULT nextval('gathering.sequence_gathering'::regclass) PRIMARY KEY,
	id_owner INT NOT NULL, -- createdBy and the person in charge of the event
	year INT DEFAULT EXTRACT(YEAR FROM CURRENT_DATE),
    name VARCHAR(20),
	CONSTRAINT fk_gathering_player FOREIGN KEY (id_owner) REFERENCES gathering.player(id)
);

-- Create transaction_type table
CREATE TABLE gathering.transaction_type (
    id INT DEFAULT nextval('gathering.sequence_transaction_type'::regclass) PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

-- Create transaction table
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

-- Create format table
CREATE TABLE gathering.format (
    id INT DEFAULT nextval('gathering.sequence_format'::regclass) PRIMARY KEY,
    name VARCHAR(20) NOT NULL,
    life_count INT NOT NULL
);

-- Create event table
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

-- Create Round table
CREATE TABLE gathering.round (
    id INT DEFAULT nextval('gathering.sequence_round'::regclass) PRIMARY KEY,
    id_event INT NOT NULL,
    id_format INT NOT NULL,
    id_player_winner INT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    round INT NOT NULL,
    players INT NOT NULL DEFAULT 0 CHECK (players >= 0),
    prize NUMERIC(10,2) NOT NULL DEFAULT 0 CHECK (prize >= 0),
    canceled BOOLEAN NOT NULL DEFAULT false,
    CONSTRAINT fk_round_event FOREIGN KEY (id_event) REFERENCES gathering.event(id),
    CONSTRAINT fk_round_format FOREIGN KEY (id_format) REFERENCES gathering.format(id),
    CONSTRAINT fk_round_player_winner FOREIGN KEY (id_player_winner) REFERENCES gathering.player(id)
);

-- Create Score (Round_Player) NxN table
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

-- Create Rank (Event_Player) NxN table
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

-- View of player balance by event
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
    e.id, p.id

-- View of balance
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
