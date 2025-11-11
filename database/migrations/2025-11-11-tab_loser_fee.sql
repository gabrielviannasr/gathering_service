CREATE SEQUENCE gathering.sequence_event_fee START 1;

CREATE TABLE gathering.event_fee (
    id INT DEFAULT nextval('gathering.sequence_event_fee'::regclass) PRIMARY KEY,
    id_event INT NOT NULL,
    players INT NOT NULL CHECK (players >= 0),
    prize_fee NUMERIC(10,2) NOT NULL DEFAULT 0 CHECK (prize_fee >= 0),
    loser_fee NUMERIC(10,2) NOT NULL DEFAULT 0 CHECK (loser_fee >= 0),
    CONSTRAINT fk_event_fee_event FOREIGN KEY (id_event) REFERENCES gathering.event(id),
    CONSTRAINT uq_event_fee_event_players UNIQUE (id_event, players)
);

COMMENT ON TABLE gathering.event_fee IS
'Armazena as taxas de distribuição de valores (fees) configuradas para cada evento,
de forma dinâmica por quantidade de jogadores.
Cada registro define quanto do valor total arrecadado é destinado aos vencedores (prize_fee)
e quanto vai para o pote dos derrotados (loser_fee).';

COMMENT ON COLUMN gathering.event_fee.players IS
'Número de jogadores na rodada que determina o valor da taxa de distribuição.';

COMMENT ON COLUMN gathering.event_fee.prize_fee IS
'Parcela do valor total da rodada destinada aos vencedores.';

COMMENT ON COLUMN gathering.event_fee.loser_fee IS
'Parcela do valor total da rodada destinada ao pote dos derrotados.';