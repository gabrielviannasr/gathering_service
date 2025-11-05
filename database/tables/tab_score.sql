-- With possible future features, but missing unique constraint
CREATE SEQUENCE gathering.sequence_score START 1;

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