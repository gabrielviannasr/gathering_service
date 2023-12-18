-- Insert player from Gathering 2023
INSERT INTO gathering.player (id, name, username, email, password, wallet) VALUES
( 1, 'Anderson Dias', 			'dias', 			'dias@example.com', 			'password', 0.0),
( 2, 'Arthur Leal', 			'moon', 			'moon@example.com', 			'password', 0.0),
( 3, 'Cindomar Ferreira', 		'cidao', 			'cidao@example.com', 			'password', 0.0),
( 4, 'Eder Vaz', 				'bengu', 			'bengu@example.com', 			'password', 0.0),
( 5, 'Felipe Wagner', 			'felipe', 			'felipe@example.com', 			'password', 0.0),
( 6, 'Gabriel dos Prazeres', 	'prazeres', 		'prazeres@example.com', 		'password', 0.0),
( 7, 'Gabriel Vianna', 			'gabrielviannasr', 	'gabriel.viannasr@gmail.com', 	'password', 0.0),
( 8, 'Jean Benevides', 			'jean', 			'jean@example.com', 			'password', 0.0),
( 9, 'Reinaldo Junior', 		'reijunior', 		'reijunior@example.com', 		'password', 0.0),
(10, 'Tobias de Souza', 		'tobogas', 			'tobogas@example.com', 			'password', 0.0),
(11, 'Valmir Vicente', 			'valmir', 			'valmir@example.com', 			'password', 0.0);

-- Insert values into gathering
INSERT INTO gathering.gathering (id, name, year) VALUES
(1, 'Dias House', 2023);

-- Insert values into event
INSERT INTO gathering.event (id, id_gathering, id_format, created_at, registration_fee, prize, players, rounds, confra_fee5, confra_fee6, confra_pot, loser_fee5, loser_fee6, loser_pot) VALUES
(1, 1, 1, CURRENT_TIMESTAMP, 20.0, 90.0, 0, 0, 0.0, 0.0, 0.0, 10.0, 30.0, 0.0);

-- Insert values into round
INSERT INTO gathering.round (id, id_event, id_format, id_player_winner, created_at, round, players, prize_taken, canceled) VALUES
( 1, 1, 1, 3, CURRENT_TIMESTAMP,  1, 6, 0, false),
( 2, 1, 1, 4, CURRENT_TIMESTAMP,  2, 6, 0, false),
( 3, 1, 1, 8, CURRENT_TIMESTAMP,  3, 6, 0, false),
( 4, 1, 1, 1, CURRENT_TIMESTAMP,  4, 6, 0, false),
( 5, 1, 1, 9, CURRENT_TIMESTAMP,  5, 6, 0, false),
( 6, 1, 1, 8, CURRENT_TIMESTAMP,  6, 6, 0, false),
( 7, 1, 1, 3, CURRENT_TIMESTAMP,  7, 6, 0, false),
( 8, 1, 1, 7, CURRENT_TIMESTAMP,  8, 6, 0, false),
( 9, 1, 1, 4, CURRENT_TIMESTAMP,  9, 6, 0, false),
(10, 1, 1, 2, CURRENT_TIMESTAMP, 10, 6, 0, false),
(11, 1, 1, 1, CURRENT_TIMESTAMP, 11, 6, 0, false),
(12, 1, 1, 4, CURRENT_TIMESTAMP, 12, 6, 0, false),
(13, 1, 1, 8, CURRENT_TIMESTAMP, 13, 6, 0, false);

-- Insert values into round_player
INSERT INTO gathering.round_player (id_round, id_player, id_player_killed_by, rank, 
primary_commander_name, primary_commander_count, secondary_commander_name, secondary_commander_count, infect_count, life_count) VALUES
-- Round 1
(1, 1, NULL, NULL, 'Primary1', 0, 'Secondary1', 0, 0, 40),
(1, 2, NULL, NULL, 'Primary2', 0, 'Secondary2', 0, 0, 40),
(1, 3, NULL,    1, 'Primary3', 0, 'Secondary3', 0, 0, 40),
(1, 4, NULL, NULL, 'Primary4', 0, 'Secondary4', 0, 0, 40),
(1, 5, NULL, NULL, 'Primary5', 0, 'Secondary5', 0, 0, 40),
(1, 9, NULL, NULL, 'Primary6', 0, 'Secondary6', 0, 0, 40),
-- Round 2
(2,  1, NULL, NULL, 'Primary1', 0, 'Secondary1', 0, 0, 40),
(2,  3, NULL, NULL, 'Primary2', 0, 'Secondary2', 0, 0, 40),
(2,  4, NULL,    1, 'Primary3', 0, 'Secondary3', 0, 0, 40),
(2,  5, NULL, NULL, 'Primary4', 0, 'Secondary4', 0, 0, 40),
(2,  9, NULL, NULL, 'Primary5', 0, 'Secondary5', 0, 0, 40),
(2, 10, NULL, NULL, 'Primary6', 0, 'Secondary6', 0, 0, 40),
-- Round 3
(3,  3, NULL, NULL, 'Primary1', 0, 'Secondary1', 0, 0, 40),
(3,  4, NULL, NULL, 'Primary2', 0, 'Secondary2', 0, 0, 40),
(3,  5, NULL, NULL, 'Primary3', 0, 'Secondary3', 0, 0, 40),
(3,  7, NULL, NULL, 'Primary4', 0, 'Secondary4', 0, 0, 40),
(3,  8, NULL,    1, 'Primary5', 0, 'Secondary5', 0, 0, 40),
(3, 11, NULL, NULL, 'Primary6', 0, 'Secondary6', 0, 0, 40),
-- Round 4
(4,  1, NULL,    1, 'Primary1', 0, 'Secondary1', 0, 0, 40),
(4,  7, NULL, NULL, 'Primary2', 0, 'Secondary2', 0, 0, 40),
(4,  8, NULL, NULL, 'Primary3', 0, 'Secondary3', 0, 0, 40),
(4,  9, NULL, NULL, 'Primary4', 0, 'Secondary4', 0, 0, 40),
(4, 10, NULL, NULL, 'Primary5', 0, 'Secondary5', 0, 0, 40),
(4, 11, NULL, NULL, 'Primary6', 0, 'Secondary6', 0, 0, 40),
-- Round 5
(5,  1, NULL, NULL, 'Primary1', 0, 'Secondary1', 0, 0, 40),
(5,  3, NULL, NULL, 'Primary2', 0, 'Secondary2', 0, 0, 40),
(5,  4, NULL, NULL, 'Primary3', 0, 'Secondary3', 0, 0, 40),
(5,  5, NULL, NULL, 'Primary4', 0, 'Secondary4', 0, 0, 40),
(5,  9, NULL,    1, 'Primary5', 0, 'Secondary5', 0, 0, 40),
(5, 10, NULL, NULL, 'Primary6', 0, 'Secondary6', 0, 0, 40),
-- Round 6
(6,  5, NULL, NULL, 'Primary1', 0, 'Secondary1', 0, 0, 40),
(6,  6, NULL, NULL, 'Primary2', 0, 'Secondary2', 0, 0, 40),
(6,  7, NULL, NULL, 'Primary3', 0, 'Secondary3', 0, 0, 40),
(6,  8, NULL,    1, 'Primary4', 0, 'Secondary4', 0, 0, 40),
(6,  9, NULL, NULL, 'Primary5', 0, 'Secondary5', 0, 0, 40),
(6, 11, NULL, NULL, 'Primary6', 0, 'Secondary6', 0, 0, 40),
-- Round 7
(7,  1, NULL, NULL, 'Primary1', 0, 'Secondary1', 0, 0, 40),
(7,  2, NULL, NULL, 'Primary2', 0, 'Secondary2', 0, 0, 40),
(7,  3, NULL,    1, 'Primary3', 0, 'Secondary3', 0, 0, 40),
(7,  4, NULL, NULL, 'Primary4', 0, 'Secondary4', 0, 0, 40),
(7,  8, NULL, NULL, 'Primary5', 0, 'Secondary5', 0, 0, 40),
(7, 10, NULL, NULL, 'Primary6', 0, 'Secondary6', 0, 0, 40),
-- Round 8 / 3 4 7 9 / VALMIR, FELIPE, JR, DR, CIDAO, BENGU
(8,  3, NULL, NULL, 'Primary1', 0, 'Secondary1', 0, 0, 40),
(8,  4, NULL, NULL, 'Primary2', 0, 'Secondary2', 0, 0, 40),
(8,  5, NULL, NULL, 'Primary3', 0, 'Secondary3', 0, 0, 40),
(8,  7, NULL,    1, 'Primary4', 0, 'Secondary4', 0, 0, 40),
(8,  9, NULL, NULL, 'Primary5', 0, 'Secondary5', 0, 0, 40),
(8, 11, NULL, NULL, 'Primary6', 0, 'Secondary6', 0, 0, 40),
-- Round 9 / 
(9, 1, NULL, NULL, 'Primary1', 0, 'Secondary1', 0, 0, 40),
(9, 4, NULL,    1, 'Primary2', 0, 'Secondary2', 0, 0, 40),
(9, 5, NULL, NULL, 'Primary3', 0, 'Secondary3', 0, 0, 40),
(9, 7, NULL, NULL, 'Primary4', 0, 'Secondary4', 0, 0, 40),
(9, 8, NULL, NULL, 'Primary5', 0, 'Secondary5', 0, 0, 40),
(9, 9, NULL, NULL, 'Primary6', 0, 'Secondary6', 0, 0, 40),
-- Round 10
(10, 1, NULL, NULL, 'Primary1', 0, 'Secondary1', 0, 0, 40),
(10, 2, NULL,    1, 'Primary2', 0, 'Secondary2', 0, 0, 40),
(10, 3, NULL, NULL, 'Primary3', 0, 'Secondary3', 0, 0, 40),
(10, 4, NULL, NULL, 'Primary4', 0, 'Secondary4', 0, 0, 40),
(10, 8, NULL, NULL, 'Primary5', 0, 'Secondary5', 0, 0, 40),
(10, 9, NULL, NULL, 'Primary6', 0, 'Secondary6', 0, 0, 40),
-- Round 11
(11, 1, NULL,    1, 'Primary1', 0, 'Secondary1', 0, 0, 40),
(11, 2, NULL, NULL, 'Primary2', 0, 'Secondary2', 0, 0, 40),
(11, 3, NULL, NULL, 'Primary3', 0, 'Secondary3', 0, 0, 40),
(11, 4, NULL, NULL, 'Primary4', 0, 'Secondary4', 0, 0, 40),
(11, 8, NULL, NULL, 'Primary5', 0, 'Secondary5', 0, 0, 40),
(11, 9, NULL, NULL, 'Primary6', 0, 'Secondary6', 0, 0, 40),
-- Round 12
(12, 1, NULL, NULL, 'Primary1', 0, 'Secondary1', 0, 0, 40),
(12, 2, NULL, NULL, 'Primary2', 0, 'Secondary2', 0, 0, 40),
(12, 3, NULL, NULL, 'Primary3', 0, 'Secondary3', 0, 0, 40),
(12, 4, NULL,    1, 'Primary4', 0, 'Secondary4', 0, 0, 40),
(12, 8, NULL, NULL, 'Primary5', 0, 'Secondary5', 0, 0, 40),
(12, 9, NULL, NULL, 'Primary6', 0, 'Secondary6', 0, 0, 40),
-- Round 13
(13, 1, NULL, NULL, 'Primary1', 0, 'Secondary1', 0, 0, 40),
(13, 2, NULL, NULL, 'Primary2', 0, 'Secondary2', 0, 0, 40),
(13, 3, NULL, NULL, 'Primary3', 0, 'Secondary3', 0, 0, 40),
(13, 4, NULL, NULL, 'Primary4', 0, 'Secondary4', 0, 0, 40),
(13, 8, NULL,    1, 'Primary5', 0, 'Secondary5', 0, 0, 40),
(13, 9, NULL, NULL, 'Primary6', 0, 'Secondary6', 0, 0, 40);