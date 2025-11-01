INSERT INTO gathering.player
(id, name, wallet) VALUES
(1, 'Anderson Dias', 0),
(2, 'Arthur Leal', 0),
(3, 'Cindomar Ferreira', 0),
(4, 'Gabriel Vianna', 0),
(5, 'Jean Benevides', 0),
(6, 'Jhonny Dias', 0),
(7, 'Tobias Souza', 0),
(8, 'Valmir Vicente', 0);

INSERT INTO gathering.gathering
(id, id_owner, name, year) VALUES
(1, 1, 'DIRETORIA', 2025);

INSERT INTO gathering.event
(id, id_gathering, id_format, created_at, players, rounds, confra_fee, round_fee, loser_fee4, loser_fee5, loser_fee6, loser_pot, confra_pot) VALUES
(1, 1, 1, CURRENT_TIMESTAMP, 8, 8, 20.0, 10.0, 0.0, 5.0, 15.0, 120.0, 160.0);

INSERT INTO gathering.round
(id, id_event, id_format, id_player_winner, created_at, round, players, canceled) VALUES
(1, 1, 1, 5, CURRENT_TIMESTAMP, 1, 6, false),
(2, 1, 1, 7, CURRENT_TIMESTAMP, 1, 6, false),
(3, 1, 1, 8, CURRENT_TIMESTAMP, 1, 6, false),
(4, 1, 1, 1, CURRENT_TIMESTAMP, 1, 6, false),
(5, 1, 1, 7, CURRENT_TIMESTAMP, 1, 6, false),
(6, 1, 1, 6, CURRENT_TIMESTAMP, 1, 6, false),
(7, 1, 1, 2, CURRENT_TIMESTAMP, 1, 6, false), -- TEST cancel this round
(8, 1, 1, 6, CURRENT_TIMESTAMP, 1, 6, false);