CREATE SEQUENCE gathering.user_account START 1;

CREATE TABLE gathering.user_account (
    id SERIAL PRIMARY KEY,
    username VARCHAR(30) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    id_player INT UNIQUE REFERENCES gathering.player(id)
);
