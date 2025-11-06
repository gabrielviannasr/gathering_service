INSERT INTO gathering.player
(id, name) VALUES
(1, 'Anderson Dias'),
(2, 'Arthur Leal'),
(3, 'Cindomar Ferreira'),
(4, 'Gabriel Vianna'),
(5, 'Jean Benevides'),
(6, 'Jhonny Dias'),
(7, 'Tobias Souza'),
(8, 'Valmir Vicente');

INSERT INTO gathering.gathering
(id, id_owner, name, year) VALUES
(1, 1, 'DIRETORIA', 2025);

INSERT INTO gathering.event
(id, id_gathering, id_format, created_at, players, rounds, confra_fee, round_fee, loser_fee4, loser_fee5, loser_fee6, loser_pot, confra_pot) VALUES
(1, 1, 1, CURRENT_TIMESTAMP, 0, 0, 20.0, 10.0, 0.0, 10.0, 15.0, 0.0, 0.0);
-- (1, 1, 1, CURRENT_TIMESTAMP, 8, 8, 20.0, 10.0, 0.0, 10.0, 15.0, 120.0, 160.0); -- Expected result after finish the event

INSERT INTO gathering.round
(id, id_event, id_format, id_player_winner, created_at, round, players, canceled) VALUES
(1, 1, 1, 5, CURRENT_TIMESTAMP, 1, 6, false),
(2, 1, 1, 7, CURRENT_TIMESTAMP, 2, 6, false),
(3, 1, 1, 8, CURRENT_TIMESTAMP, 3, 6, false),
(4, 1, 1, 1, CURRENT_TIMESTAMP, 4, 6, false),
(5, 1, 1, 7, CURRENT_TIMESTAMP, 5, 6, false),
(6, 1, 1, 6, CURRENT_TIMESTAMP, 6, 6, false),
(7, 1, 1, 2, CURRENT_TIMESTAMP, 7, 6, false), -- TEST cancel this round
(8, 1, 1, 6, CURRENT_TIMESTAMP, 8, 6, false);

INSERT INTO gathering.score
(id_round, id_player) VALUES
-- Round 1
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(1, 5),
(1, 6),
-- Round 2
(2, 1),
(2, 4),
(2, 5),
(2, 6),
(2, 7),
(2, 8),
-- Round 3
(3, 1),
(3, 2),
(3, 3),
(3, 5),
(3, 7),
(3, 8),
-- Round 4
(4, 1),
(4, 2),
(4, 3),
(4, 5),
(4, 6),
(4, 8),
-- Round 5
(5, 1),
(5, 3),
(5, 4),
(5, 5),
(5, 7),
(5, 8),
-- Round 6
(6, 1),
(6, 2),
(6, 3),
(6, 4),
(6, 5),
(6, 6),
-- Round 7
(7, 2),
(7, 3),
(7, 4),
(7, 5),
(7, 6),
(7, 8),
-- Round 8
(8, 1),
(8, 4),
(8, 5),
(8, 6),
(8, 7),
(8, 8);
