CREATE TABLE gathering.account (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    username VARCHAR(30) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    cpf CHAR(11) UNIQUE,
    plan VARCHAR(20) DEFAULT 'BASIC',
    plan_start DATE DEFAULT CURRENT_DATE,
    plan_expiration DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE gathering.plan (
    id SERIAL PRIMARY KEY,
    name VARCHAR(20) UNIQUE,
    max_events INT,
    max_players INT,
    max_rounds INT,
    has_dashboard BOOLEAN
);
