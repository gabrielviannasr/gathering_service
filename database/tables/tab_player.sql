CREATE SEQUENCE gathering.sequence_player START 1;a

CREATE TABLE gathering.player (
    id INT DEFAULT nextval('gathering.sequence_player'::regclass) PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    username VARCHAR(20) UNIQUE,
    email VARCHAR(50) UNIQUE,
    password VARCHAR(20),
    wallet NUMERIC NOT NULL DEFAULT 0
);
