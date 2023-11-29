CREATE TABLE gathering.player (
    id INT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    username VARCHAR(20) UNIQUE,
    email VARCHAR(50) UNIQUE,
    password VARCHAR(20),
    wallet NUMERIC NOT NULL DEFAULT 0
);

CREATE TABLE gathering.payment (
    id INT PRIMARY KEY,
    id_player INT NOT NULL,
    created_at DATE NOT NULL,
    invoice NUMERIC NOT NULL DEFAULT 0,
    description VARCHAR(500),
    CONSTRAINT fk_payment_player FOREIGN KEY (id_player) REFERENCES gathering.Player(id)
);

CREATE TABLE gathering.format (
    id INT PRIMARY KEY,
    name VARCHAR(20) NOT NULL,
    life_count INT NOT NULL
);

CREATE TABLE gathering.event (
    id INT PRIMARY KEY,
    id_format INT,
    created_at DATE NOT NULL,
    registration_fee NUMERIC NOT NULL DEFAULT 0,
    players INT NOT NULL DEFAULT 0,
    rounds INT NOT NULL DEFAULT 0,
    confra_fee5 NUMERIC NOT NULL DEFAULT 0,
    confra_fee6 NUMERIC NOT NULL DEFAULT 0,
    confra_pot NUMERIC NOT NULL DEFAULT 0,
    loser_fee5 NUMERIC NOT NULL DEFAULT 0,
    loser_fee6 NUMERIC NOT NULL DEFAULT 0,
    loser_pot NUMERIC NOT NULL DEFAULT 0,
    CONSTRAINT fk_event_format FOREIGN KEY (id_format) REFERENCES gathering.Format(id)
);

CREATE TABLE gathering.round (
    id INT PRIMARY KEY,
    id_event INT NOT NULL,
    id_format INT,
    id_player_winner INT,
    created_at DATE NOT NULL,
    round INT NOT NULL,
    players INT NOT NULL,
    boosters_prize INT NOT NULL DEFAULT 0,
    canceled BOOLEAN NOT NULL DEFAULT false,
    CONSTRAINT fk_round_event FOREIGN KEY (id_event) REFERENCES gathering.Event(id),
    CONSTRAINT fk_round_format FOREIGN KEY (id_format) REFERENCES gathering.Format(id),
    CONSTRAINT fk_round_player_winner FOREIGN KEY (id_player_winner) REFERENCES gathering.Player(id)
);

CREATE TABLE gathering.round_player (
    id INT PRIMARY KEY,
    id_round INT NOT NULL,
    id_player INT NOT NULL,
    id_player_killed_by INT,
    rank INT NOT NULL DEFAULT 1,
    primary_commander_name VARCHAR,
    primary_commander_count INT NOT NULL DEFAULT 0,
    secondary_commander_name VARCHAR,
    secondary_commander_count INT,
    infect_count INT NOT NULL DEFAULT 0,
    life_count INT NOT NULL,
    CONSTRAINT fk_round_player FOREIGN KEY (id_round) REFERENCES gathering.Round(id),
    CONSTRAINT fk_round_player_killed_by FOREIGN KEY (id_player_killed_by) REFERENCES gathering.Player(id),
    CONSTRAINT fk_round_player_id FOREIGN KEY (id_player) REFERENCES gathering.Player(id)
);

-- Create rank table
CREATE TABLE gathering.rank (
    id INT PRIMARY KEY,
    id_event INT NOT NULL,
    id_player INT NOT NULL,
    rank INT NOT NULL DEFAULT 1,
    wins INT NOT NULL DEFAULT 0,
    rounds INT NOT NULL DEFAULT 0,
    balance NUMERIC NOT NULL DEFAULT 0,
    loser_pot NUMERIC NOT NULL DEFAULT 0,
    boosters_prize INT NOT NULL DEFAULT 0,
    boosters_balance NUMERIC NOT NULL DEFAULT 0,
    wallet NUMERIC NOT NULL DEFAULT 0,
    CONSTRAINT fk_rank_event FOREIGN KEY (id_event) REFERENCES gathering.Event(id),
    CONSTRAINT fk_rank_player FOREIGN KEY (id_player) REFERENCES gathering.Player(id)
);
