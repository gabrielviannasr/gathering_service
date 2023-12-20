-- Create sequences
CREATE SEQUENCE gathering.sequence_gathering START 2;
CREATE SEQUENCE gathering.sequence_player START 12;
CREATE SEQUENCE gathering.sequence_transaction_type START 3;
CREATE SEQUENCE gathering.sequence_transaction START 1;
CREATE SEQUENCE gathering.sequence_format START 4;
CREATE SEQUENCE gathering.sequence_event START 2;
CREATE SEQUENCE gathering.sequence_round START 14;
CREATE SEQUENCE gathering.sequence_round_player START 1;
CREATE SEQUENCE gathering.sequence_rank START 1;

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
	id_player INT NOT NULL, -- createdBy
	year INT DEFAULT EXTRACT(YEAR FROM CURRENT_DATE),
    name VARCHAR(20),
	CONSTRAINT fk_gathering_player FOREIGN KEY (id_player) REFERENCES gathering.player(id)
);

-- Create transaction_type table
CREATE TABLE gathering.transaction_type (
    id INT DEFAULT nextval('gathering.sequence_gathering'::regclass) PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

-- Insert transaction_type table initial load
INSERT INTO gathering.transaction_type (id, name) VALUES
(1, 'Payment'),
(2, 'Withdrawal');

-- Create transaction table
CREATE TABLE gathering.transaction (
    id INT DEFAULT nextval('gathering.sequence_transaction'::regclass) PRIMARY KEY,
    id_player INT NOT NULL,
    id_gathering INT,
    id_transaction_type INT NOT NULL,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    amount NUMERIC NOT NULL,    
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

-- Insert format table initial load
INSERT INTO gathering.format (id, name, life_count) VALUES
(1, 'Commander', 40),
(2, 'Conquest', 30),
(3, 'Tiny Leader', 30);

-- Create event table
CREATE TABLE gathering.event (
    id INT DEFAULT nextval('gathering.sequence_event'::regclass) PRIMARY KEY,
    id_gathering INT NOT NULL,
    id_format INT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    registration_fee NUMERIC NOT NULL DEFAULT 0,
	prize NUMERIC NOT NULL DEFAULT 0,
    players INT NOT NULL DEFAULT 0,
    rounds INT NOT NULL DEFAULT 0,
    confra_fee5 NUMERIC NOT NULL DEFAULT 0,
    confra_fee6 NUMERIC NOT NULL DEFAULT 0,
    confra_pot NUMERIC NOT NULL DEFAULT 0,
    loser_fee5 NUMERIC NOT NULL DEFAULT 0,
    loser_fee6 NUMERIC NOT NULL DEFAULT 0,
    loser_pot NUMERIC NOT NULL DEFAULT 0,
	CONSTRAINT fk_event_gathering FOREIGN KEY (id_gathering) REFERENCES gathering.gathering(id),
    CONSTRAINT fk_event_format FOREIGN KEY (id_format) REFERENCES gathering.format(id)
);

-- Create Round table
CREATE TABLE gathering.round (
    id INT DEFAULT nextval('gathering.sequence_round'::regclass) PRIMARY KEY,
    id_event INT NOT NULL,
    id_format INT,
    id_player_winner INT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    round INT NOT NULL,
    players INT NOT NULL DEFAULT 0,
    prize_taken NUMERIC NOT NULL DEFAULT 0,
    canceled BOOLEAN NOT NULL DEFAULT false,
    CONSTRAINT fk_round_event FOREIGN KEY (id_event) REFERENCES gathering.event(id),
    CONSTRAINT fk_round_format FOREIGN KEY (id_format) REFERENCES gathering.format(id),
    CONSTRAINT fk_round_player_winner FOREIGN KEY (id_player_winner) REFERENCES gathering.player(id)
);

-- Create Round_player table
CREATE TABLE gathering.round_player (
    id INT DEFAULT nextval('gathering.sequence_round_player'::regclass) PRIMARY KEY,
    id_round INT NOT NULL,
    id_player INT NOT NULL,
    id_player_killed_by INT,
    rank INT,
    primary_commander_name VARCHAR,
    primary_commander_count INT NOT NULL DEFAULT 0,
    secondary_commander_name VARCHAR,
    secondary_commander_count INT,
    infect_count INT NOT NULL DEFAULT 0,
    life_count INT NOT NULL,
    CONSTRAINT fk_round_player FOREIGN KEY (id_round) REFERENCES gathering.Round(id),
    CONSTRAINT fk_round_player_killed_by FOREIGN KEY (id_player_killed_by) REFERENCES gathering.player(id),
    CONSTRAINT fk_round_player_id FOREIGN KEY (id_player) REFERENCES gathering.player(id)
);

-- Create Rank table
CREATE TABLE gathering.rank (
    id INT DEFAULT nextval('gathering.sequence_rank'::regclass) PRIMARY KEY,
    id_event INT NOT NULL,
	id_player INT NOT NULL,
	rank INT,
    wins INT NOT NULL DEFAULT 0,
    rounds INT NOT NULL DEFAULT 0,
	positive NUMERIC NOT NULL DEFAULT 0,
	negative NUMERIC NOT NULL DEFAULT 0,
    rank_balance NUMERIC NOT NULL DEFAULT 0,
    loser_pot NUMERIC NOT NULL DEFAULT 0,
    prize_taken NUMERIC NOT NULL DEFAULT 0,
    final_balance NUMERIC NOT NULL DEFAULT 0,
    CONSTRAINT fk_rank_event FOREIGN KEY (id_event) REFERENCES gathering.event(id),
    CONSTRAINT fk_rank_player FOREIGN KEY (id_player) REFERENCES gathering.player(id)
);

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