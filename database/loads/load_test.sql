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
(id, id_player, name, year) VALUES
(1, 1, 'DIRETORIA', 2025);

INSERT INTO gathering.event
(id, id_gathering, id_format, confra_fee, round_fee, loser_fee4, loser_fee5, loser_fee6) VALUES
(1, 1, 1, 20.0, 0.0, 10.0, 10.0, 15.0);

/**
 * COLUMN		| INITIAL	| EXPECTED
 * players		|	   0	|		8
 * rounds		|	   0	|		8
 * confra_fee	|	20.0	|	 20.0
 * round_fee	|	10.0	|	 10.0
 * loser_fee4	|	 0.0	|	  0.0
 * loser_fee5	|	10.0	|	 10.0
 * loser_fee6	|	15.0	|	 15.0
 * loser_pot	|    0.0	|	120.0
 * confra_pot	|    0.0	|	160.0
 * prize		| 	 0.0	|	360.0
 */

INSERT INTO gathering.round
(id, id_event, id_format, id_player_winner, round, players, prize, loser_pot, canceled) VALUES
(1, 1, 1, 5, 1, 6, 45.0, 15.0, false),
(2, 1, 1, 7, 2, 6, 45.0, 15.0, false),
(3, 1, 1, 8, 3, 6, 45.0, 15.0, false),
(4, 1, 1, 1, 4, 6, 45.0, 15.0, false),
(5, 1, 1, 7, 5, 6, 45.0, 15.0, false),
(6, 1, 1, 6, 6, 6, 45.0, 15.0, false),
(7, 1, 1, 2, 7, 6, 45.0, 15.0, false), -- TEST cancel this round
(8, 1, 1, 6, 8, 6, 45.0, 15.0, false);

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

--INSERT INTO gathering.transaction
--(id_gathering, id_event, id_player, id_transaction_type, created_at, amount)
--VALUES
--(1, 1, 4, 3, CURRENT_TIMESTAMP, 35.5), -- Gabriel
--(1, 1, 5, 3, CURRENT_TIMESTAMP, 5), -- Jean
--(1, 1, 7, 4, CURRENT_TIMESTAMP, -5); -- Tobias