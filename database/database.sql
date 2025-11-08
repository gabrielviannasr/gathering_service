/* CREATE SEQUENCES */
CREATE SEQUENCE gathering.sequence_event START 1;
CREATE SEQUENCE gathering.sequence_format START 1;
CREATE SEQUENCE gathering.sequence_gathering START 1;
CREATE SEQUENCE gathering.sequence_player START 1;
CREATE SEQUENCE gathering.sequence_result START 1;
CREATE SEQUENCE gathering.sequence_round START 1;
CREATE SEQUENCE gathering.sequence_score START 1;
CREATE SEQUENCE gathering.sequence_transaction START 1;
CREATE SEQUENCE gathering.sequence_transaction_type START 1;

/* CREATE SEQUENCES */

/* CREATE TABLES */
CREATE TABLE gathering.player (
    id INT DEFAULT nextval('gathering.sequence_player'::regclass) PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

COMMENT ON TABLE gathering.player IS
'Represents a participant of a gathering. Personal and financial details are handled in related entities.';

CREATE TABLE gathering.gathering (
    id INT DEFAULT nextval('gathering.sequence_gathering'::regclass) PRIMARY KEY,
	id_player INT NOT NULL, -- createdBy and the person in charge of the event
	year INT DEFAULT EXTRACT(YEAR FROM CURRENT_DATE),
    name VARCHAR(20),
	CONSTRAINT fk_gathering_player FOREIGN KEY (id_player) REFERENCES gathering.player(id)
);

CREATE TABLE gathering.transaction_type (
    id INT DEFAULT nextval('gathering.sequence_transaction_type'::regclass) PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(100)
);

COMMENT ON TABLE gathering.transaction_type IS
'Defines the available financial transaction types used in the system. Each type represents a specific player operation, such as deposits, withdrawals, or event fees.';

CREATE TABLE gathering.transaction (
    id INT DEFAULT nextval('gathering.sequence_transaction'::regclass) PRIMARY KEY,
    id_gathering INT NOT NULL,
    id_event INT NULL,
    id_player INT NOT NULL,    
    id_transaction_type INT NOT NULL,    
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    amount NUMERIC(10,2) NOT NULL,
	description VARCHAR(500),
    CONSTRAINT fk_transaction_gathering FOREIGN KEY (id_gathering) REFERENCES gathering.gathering(id),
    CONSTRAINT fk_transaction_event FOREIGN KEY (id_event) REFERENCES gathering.event(id),
    CONSTRAINT fk_transaction_player FOREIGN KEY (id_player) REFERENCES gathering.player(id),
    CONSTRAINT fk_transaction_transaction_type FOREIGN KEY (id_transaction_type) REFERENCES gathering.transaction_type(id)
);

COMMENT ON TABLE gathering.transaction IS 
'Stores all player financial transactions.
A positive amount represents a credit, while a negative amount represents a debit.
The id_event field is optional and indicates the event that originated the transaction, if applicable.';

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
    prize NUMERIC(10,2) NOT NULL DEFAULT 0 CHECK (prize >= 0),
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
    CONSTRAINT fk_score_round FOREIGN KEY (id_round) REFERENCES gathering.round(id),
    CONSTRAINT fk_score_player FOREIGN KEY (id_player) REFERENCES gathering.player(id),
    CONSTRAINT uq_score_round_player UNIQUE (id_round, id_player)
);

COMMENT ON TABLE gathering.score IS
'Stores player participation in each round.
Each record links a player to a specific round and ensures one unique entry per player per round.';

-- ======================================================
-- Result (Event_Player)
-- Stores the final outcome of each player in an event,
-- including ranking, round stats, and balance results.
-- ======================================================
CREATE TABLE gathering.result (
    id INT DEFAULT nextval('gathering.sequence_result'::regclass) PRIMARY KEY,
    id_event INT NOT NULL,
    id_player INT NOT NULL,
    rank INT,
    wins INT NOT NULL DEFAULT 0,
    rounds INT NOT NULL DEFAULT 0,
    positive NUMERIC(10,2) NOT NULL DEFAULT 0,  -- total earned
    negative NUMERIC(10,2) NOT NULL DEFAULT 0,  -- total owed
    rank_balance NUMERIC(10,2) NOT NULL DEFAULT 0, -- net result before pot distribution
    loser_pot NUMERIC(10,2) NOT NULL DEFAULT 0, -- share of loser pot
    final_balance NUMERIC(10,2) NOT NULL DEFAULT 0, -- final result after pot distribution
    CONSTRAINT fk_result_event FOREIGN KEY (id_event) REFERENCES gathering.event(id),
    CONSTRAINT fk_result_player FOREIGN KEY (id_player) REFERENCES gathering.player(id),
    CONSTRAINT uq_result_event_player UNIQUE (id_event, id_player) -- prevents duplicates
);

-- 🏷️ Comments for documentation
COMMENT ON TABLE gathering.result IS
'Stores the final outcome of each player in an event, including their rank, round results, and final balance after pot distribution.';

COMMENT ON COLUMN gathering.result.rank IS
'Final rank of the player in the event.';

COMMENT ON COLUMN gathering.result.rank_balance IS
'Net balance of the player before loser pot distribution (positive = profit, negative = loss).';

COMMENT ON COLUMN gathering.result.final_balance IS
'Final balance of the player after loser pot distribution.';
/* CREATE TABLES */

/* CREATE VIEWS */
CREATE OR REPLACE VIEW gathering.vw_event_confra_pot AS
    SELECT
        e.id_gathering,
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
        g.id AS id_gathering,
        g.name AS gathering_name,
        e.id AS id_event,
        COUNT(r.id) AS rounds,
        SUM(r.loser_pot) AS loser_pot,
        SUM(r.prize) AS prize
    FROM
        gathering.round r
        INNER JOIN gathering.event e ON e.id = r.id_event
        INNER JOIN gathering.gathering g ON g.id = e.id_gathering
    WHERE
        r.canceled = false
    GROUP BY
        g.id, e.id;
    
    COMMENT ON VIEW gathering.vw_event_loser_pot IS
    'Displays the total number of rounds, the total prize awarded and the accumulated loser pot per event.';

CREATE OR REPLACE VIEW gathering.vw_event_player_balance AS
    WITH player_balance AS (
        SELECT
            e.id_gathering,
            e.id AS id_event,
            p.id AS id_player,
            p.name AS player_name,
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
    --	    e.id, p.id
    --		Garante portabilidade e evita warning em versões futuras.
            e.id_gathering, e.id, p.id, p.name, e.round_fee
    )
    SELECT
        id_gathering,
        id_event,
        id_player,
        player_name,
        wins,
        rounds,
        positive,
        negative,
        positive - negative AS rank_balance
    FROM
        player_balance
    ORDER BY
        player_name;

    COMMENT ON VIEW gathering.vw_event_player_balance IS
    'Displays the performance and balance of each player per event, 
    including total wins, rounds, prizes, fees, and the resulting rank balance.';

CREATE OR REPLACE VIEW gathering.vw_event_player_rank AS
    SELECT
        id_gathering,
        id_event,
        RANK() OVER (
            -- PARTITION BY id_event garante que o ranking é calculado dentro de cada evento individualmente.
            PARTITION BY id_event
            ORDER BY rank_balance DESC, rounds ASC
        ) AS rank,
        id_player,
        player_name,
        wins,
        rounds,
        positive,
        negative,
        rank_balance
    FROM
        gathering.vw_event_player_balance
    ORDER BY
        id_event, rank, player_name;

    COMMENT ON VIEW gathering.vw_event_player_rank IS
    'Provides the player ranking for each event based on rank balance and performance, ensuring ranks are calculated independently per event.';

CREATE OR REPLACE VIEW gathering.vw_event_rank_count AS
    SELECT
        id_gathering,
        id_event,
        rank,
        COUNT(*)
    FROM
        gathering.vw_event_player_rank
    GROUP BY
        id_gathering, id_event, rank
    ORDER BY
        rank DESC;

    COMMENT ON VIEW gathering.vw_event_rank_count IS
    'Indicates how many players share each rank, used to distribute the loser pot.';

CREATE OR REPLACE VIEW gathering.vw_gathering_confra_pot_total AS
    SELECT
        g.id AS id_gathering,
        g.name AS gathering_name,
        COUNT(e.id) AS total_events,
        COALESCE(SUM(e.rounds), 0) AS total_rounds,
        COALESCE(SUM(e.confra_pot), 0) AS total_confra_pot
    FROM
        gathering.gathering g
        INNER JOIN gathering.event e ON e.id_gathering = g.id
    GROUP BY
        g.id, g.name
    ORDER BY
        g.name;

    COMMENT ON VIEW gathering.vw_gathering_confra_pot_total IS
    'Displays the total accumulated confra pot, total rounds, and number of events for each gathering.';

CREATE OR REPLACE VIEW gathering.vw_gathering_loser_pot AS
    SELECT
        g.id AS id_gathering,
        g.name AS gathering_name,
        COALESCE(SUM(r.prize), 0) AS prize,
        COALESCE(SUM(r.loser_pot), 0) AS loser_pot
    FROM
        gathering.gathering g
        INNER JOIN gathering.event e ON e.id_gathering = g.id
        INNER JOIN gathering.round r ON r.id_event = e.id
    WHERE
        r.canceled = false
    GROUP BY
        g.id
    ORDER BY
        g.name;
    
    COMMENT ON VIEW gathering.vw_gathering_loser_pot IS
    'Summarizes the total prizes and loser pots accumulated across all events within each gathering.';

CREATE OR REPLACE VIEW gathering.vw_gathering_format AS
    SELECT
        g.id AS id_gathering,
        g.name AS gathering_name,
        f.id AS id_format,
        f.name AS format_name,
        COUNT(r.id) AS rounds
    FROM
        gathering.gathering g
        INNER JOIN gathering.event e ON e.id_gathering = g.id
        INNER JOIN gathering.round r ON r.id_event = e.id
        INNER JOIN gathering.format f ON r.id_format = f.id
    WHERE
        r.canceled = false
    GROUP BY
        g.id, f.id
    ORDER BY
        g.name, f.name;

    COMMENT ON VIEW gathering.vw_gathering_format IS
    'Displays the total number rounds for each format played in the gathering.';

CREATE OR REPLACE VIEW gathering.vw_gathering_player_balance AS
    SELECT
        id_gathering,
        id_player,
        player_name,
        COUNT(id_event) AS events,
        COALESCE(SUM(wins), 0) AS wins,
        COALESCE(SUM(rounds), 0) AS rounds,
        COALESCE(SUM(positive), 0) AS positive,
        COALESCE(SUM(negative), 0) AS negative,
        COALESCE(SUM(rank_balance), 0) AS rank_balance
    FROM
        gathering.vw_event_player_balance
    GROUP BY
        id_gathering, id_player, player_name
    ORDER BY
        id_gathering, player_name;

    COMMENT ON VIEW gathering.vw_gathering_player_balance IS
    'Aggregates player balances across all events within each gathering. 
    Serves as the base view for calculating the cumulative gathering-level rankings.';

CREATE OR REPLACE VIEW gathering.vw_gathering_player_rank AS
    SELECT
        id_gathering,
        RANK() OVER (
            -- PARTITION BY id_gathering garante que o ranking é calculado dentro de cada gathering individualmente.
            PARTITION BY id_gathering
            ORDER BY rank_balance DESC, rounds ASC
        ) AS rank,
        id_player,
        player_name,
        events,
        wins,
        rounds,
        positive,
        negative,
        rank_balance
    FROM
        gathering.vw_gathering_player_balance
    ORDER BY
        id_gathering, rank, player_name;

    COMMENT ON VIEW gathering.vw_gathering_player_rank IS
    'Provides the player ranking within each gathering based on cumulative performance and balance, built upon vw_gathering_player_balance.';

CREATE OR REPLACE VIEW gathering.vw_gathering_player_wallet AS
    SELECT
        g.id AS id_gathering,
        g.name AS gathering_name,
        p.id AS id_player,
        p.name AS player_name,
        COALESCE(SUM(t.amount), 0) AS wallet
    FROM
        gathering.player p
    LEFT JOIN
        gathering.transaction t ON t.id_player = p.id
    LEFT JOIN
        gathering.gathering g ON g.id = t.id_gathering
    GROUP BY
        g.id, g.name, p.id, p.name
    ORDER BY
        g.name, p.name;

COMMENT ON VIEW gathering.vw_gathering_player_wallet IS
'Displays each player''s wallet (balance) grouped by gathering, based on all related transactions.';

CREATE OR REPLACE VIEW gathering.vw_gathering_player_transaction AS
    SELECT
        g.id AS id_gathering,
        g.name AS gathering_name,
        p.id AS id_player,
        p.name AS player_name,
        t.id AS id_transaction,
        t.created_at,
        tt.name AS transaction_type_name,
        t.amount,
        t.description AS transaction_description
    FROM
        gathering.gathering g
    LEFT JOIN
        gathering.transaction t ON t.id_gathering = g.id
    LEFT JOIN
        gathering.player p ON p.id = t.id_player
    LEFT JOIN
        gathering.transaction_type tt ON tt.id = t.id_transaction_type
    ORDER BY
        g.name, p.name, t.created_at, t.id_transaction_type;

COMMENT ON VIEW gathering.vw_gathering_player_transaction IS
'Exibe as transações de cada jogador em suas respectivas confraternizações, incluindo tipo, valor e descrição. 
A ordenação segue a sequência natural do fluxo: Inscrição → Resultado → Depósito → Saque.';
/* CREATE VIEWS */

-- CREATE INDEXES
-- CREATE INDEX IF NOT EXISTS idx_gathering_format_name ON gathering.format(name);
-- CREATE INDEX IF NOT EXISTS idx_gathering_player_email ON gathering.player(email);
-- CREATE INDEX IF NOT EXISTS idx_gathering_player_name ON gathering.player(name);
-- CREATE INDEX IF NOT EXISTS idx_gathering_player_username ON gathering.player(username);
CREATE INDEX idx_rank_event_player ON gathering.rank(event_id, player_id);
