-- Create sequences
CREATE SEQUENCE gathering.sequence_player START 1;
CREATE SEQUENCE gathering.sequence_payment START 1;
CREATE SEQUENCE gathering.sequence_format START 1;
CREATE SEQUENCE gathering.sequence_event START 1;
CREATE SEQUENCE gathering.sequence_round START 1;
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

-- Create Payment table
CREATE TABLE gathering.payment (
    id INT DEFAULT nextval('gathering.sequence_payment'::regclass) PRIMARY KEY,
    id_player INT NOT NULL,
    created_at DATE NOT NULL,
    invoice NUMERIC NOT NULL DEFAULT 0,
    description VARCHAR(500),
    CONSTRAINT fk_payment_player FOREIGN KEY (id_player) REFERENCES gathering.player(id)
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
    id_format INT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    registration_fee NUMERIC NOT NULL DEFAULT 0,
    players INT NOT NULL DEFAULT 0,
    rounds INT NOT NULL DEFAULT 0,
    confra_fee5 NUMERIC NOT NULL DEFAULT 0,
    confra_fee6 NUMERIC NOT NULL DEFAULT 0,
    confra_pot NUMERIC NOT NULL DEFAULT 0,
    loser_fee5 NUMERIC NOT NULL DEFAULT 0,
    loser_fee6 NUMERIC NOT NULL DEFAULT 0,
    loser_pot NUMERIC NOT NULL DEFAULT 0,
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

