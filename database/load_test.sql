-- Insert 8 different players
INSERT INTO gathering.player (id, name, username, email, password, wallet) VALUES
(1, 'Player1', 'player1', 'player1@example.com', 'password1', 0.0),
(2, 'Player2', 'player2', 'player2@example.com', 'password2', 0.0),
(3, 'Player3', 'player3', 'player3@example.com', 'password3', 0.0),
(4, 'Player4', 'player4', 'player4@example.com', 'password4', 0.0),
(5, 'Player5', 'player5', 'player5@example.com', 'password5', 0.0),
(6, 'Player6', 'player6', 'player6@example.com', 'password6', 0.0),
(7, 'Player7', 'player7', 'player7@example.com', 'password7', 0.0),
(8, 'Player8', 'player8', 'player8@example.com', 'password8', 0.0);

-- Insert values into gathering
INSERT INTO gathering.gathering (id, name) VALUES
(1, 'Gathering'),
(2, 'Dias House');

-- Insert 3 events
INSERT INTO gathering.event (id, id_gathering, id_format, created_at, registration_fee, prize, players, rounds, confra_fee5, confra_fee6, confra_pot, loser_fee5, loser_fee6, loser_pot) VALUES
(1, 1, 1, 80.0, CURRENT_TIMESTAMP, 20.0, 0, 0, 0.0, 10.0, 0.0, 20.0, 30.0, 0.0),
(2, 2, 2, 80.0, CURRENT_TIMESTAMP, 20.0, 0, 0, 0.0, 10.0, 0.0, 20.0, 30.0, 0.0),
(3, 2, 3, 80.0, CURRENT_TIMESTAMP, 20.0, 0, 0, 0.0, 10.0, 0.0, 20.0, 30.0, 0.0);

-- Insert 8 rounds to event 1
INSERT INTO gathering.round (id, id_event, id_format, id_player_winner, created_at, round, players, prize_taken, canceled) VALUES
(1, 1, 1, 2, CURRENT_TIMESTAMP, 1, 6, 0, false),
(2, 1, 1, 3, CURRENT_TIMESTAMP, 2, 6, 0, false),
(3, 1, 1, 4, CURRENT_TIMESTAMP, 3, 6, 0, false),
(4, 1, 1, 1, CURRENT_TIMESTAMP, 4, 6, 0, false),
(5, 1, 1, 6, CURRENT_TIMESTAMP, 5, 6, 0, false),
(6, 1, 1, 2, CURRENT_TIMESTAMP, 6, 6, 0, false),
(7, 1, 1, 3, CURRENT_TIMESTAMP, 7, 6, 0, false),
(8, 1, 1, 4, CURRENT_TIMESTAMP, 8, 6, 0, false);

-- Insert 8 rounds to event 2
INSERT INTO gathering.round (id, id_event, id_format, id_player_winner, created_at, round, players, prize_taken, canceled) VALUES
( 9, 2, 2, 5, CURRENT_TIMESTAMP, 1, 6,  0, false),
(10, 2, 2, 7, CURRENT_TIMESTAMP, 2, 6, 80, false),
(11, 2, 2, 8, CURRENT_TIMESTAMP, 3, 6,  0, false),
(12, 2, 2, 1, CURRENT_TIMESTAMP, 4, 6, 80, false),
(13, 2, 2, 7, CURRENT_TIMESTAMP, 5, 6, 80, false),
(14, 2, 2, 6, CURRENT_TIMESTAMP, 6, 6,  0, false),
(15, 2, 2, 2, CURRENT_TIMESTAMP, 7, 6,  0, true), -- ROUND 7 CANCELED
(16, 2, 2, 6, CURRENT_TIMESTAMP, 8, 6,  0, false);

-- Insert 6 rounds to event 3
INSERT INTO gathering.round (id, id_event, id_format, id_player_winner, created_at, round, players, prize_taken, canceled) VALUES
(17, 3, 3, 1, CURRENT_TIMESTAMP, 1, 6, 0, false),
(18, 3, 3, 2, CURRENT_TIMESTAMP, 2, 6, 0, false),
(19, 3, 3, 3, CURRENT_TIMESTAMP, 3, 6, 0, false),
(20, 3, 3, 4, CURRENT_TIMESTAMP, 4, 6, 0, false),
(21, 3, 3, 5, CURRENT_TIMESTAMP, 5, 6, 0, false),
(22, 3, 3, 6, CURRENT_TIMESTAMP, 6, 6, 0, false);

-- Insert 8 rounds to round_player of event 1
INSERT INTO gathering.round_player (id_round, id_player, id_player_killed_by, rank, 
primary_commander_name, primary_commander_count, secondary_commander_name, secondary_commander_count, infect_count, life_count) VALUES
-- round 1
(1, 1, NULL, NULL, 'Primary1', 0, 'Secondary1', 0, 0, 40),
(1, 2, NULL, 	1, 'Primary2', 0, 'Secondary2', 0, 0, 40),
(1, 3, NULL, NULL, 'Primary3', 0, 'Secondary3', 0, 0, 40),
(1, 4, NULL, NULL, 'Primary4', 0, 'Secondary4', 0, 0, 40),
(1, 5, NULL, NULL, 'Primary5', 0, 'Secondary5', 0, 0, 40),
(1, 6, NULL, NULL, 'Primary6', 0, 'Secondary6', 0, 0, 40),
-- round 2
(2, 1, NULL, NULL, 'Primary1', 0, 'Secondary1', 0, 0, 40),
(2, 2, NULL, NULL, 'Primary2', 0, 'Secondary2', 0, 0, 40),
(2, 3, NULL, 	1, 'Primary3', 0, 'Secondary3', 0, 0, 40),
(2, 4, NULL, NULL, 'Primary4', 0, 'Secondary4', 0, 0, 40),
(2, 5, NULL, NULL, 'Primary5', 0, 'Secondary5', 0, 0, 40),
(2, 6, NULL, NULL, 'Primary6', 0, 'Secondary6', 0, 0, 40),
-- round 3
(3, 1, NULL, NULL, 'Primary1', 0, 'Secondary1', 0, 0, 40),
(3, 2, NULL, NULL, 'Primary2', 0, 'Secondary2', 0, 0, 40),
(3, 3, NULL, NULL, 'Primary3', 0, 'Secondary3', 0, 0, 40),
(3, 4, NULL, 	1, 'Primary4', 0, 'Secondary4', 0, 0, 40),
(3, 5, NULL, NULL, 'Primary5', 0, 'Secondary5', 0, 0, 40),
(3, 6, NULL, NULL, 'Primary6', 0, 'Secondary6', 0, 0, 40),
-- round 4
(4, 1, NULL, 	1, 'Primary1', 0, 'Secondary1', 0, 0, 40),
(4, 2, NULL, NULL, 'Primary2', 0, 'Secondary2', 0, 0, 40),
(4, 3, NULL, NULL, 'Primary3', 0, 'Secondary3', 0, 0, 40),
(4, 4, NULL, NULL, 'Primary4', 0, 'Secondary4', 0, 0, 40),
(4, 5, NULL, NULL, 'Primary5', 0, 'Secondary5', 0, 0, 40),
(4, 6, NULL, NULL, 'Primary6', 0, 'Secondary6', 0, 0, 40),
-- round 5
(5, 1, NULL, NULL, 'Primary1', 0, 'Secondary1', 0, 0, 40),
(5, 2, NULL, NULL, 'Primary2', 0, 'Secondary2', 0, 0, 40),
(5, 3, NULL, NULL, 'Primary3', 0, 'Secondary3', 0, 0, 40),
(5, 4, NULL, NULL, 'Primary4', 0, 'Secondary4', 0, 0, 40),
(5, 5, NULL, NULL, 'Primary5', 0, 'Secondary5', 0, 0, 40),
(5, 6, NULL, 	1, 'Primary6', 0, 'Secondary6', 0, 0, 40),
-- round 6
(6, 1, NULL, NULL, 'Primary1', 0, 'Secondary1', 0, 0, 40),
(6, 2, NULL, 	1, 'Primary2', 0, 'Secondary2', 0, 0, 40),
(6, 3, NULL, NULL, 'Primary3', 0, 'Secondary3', 0, 0, 40),
(6, 4, NULL, NULL, 'Primary4', 0, 'Secondary4', 0, 0, 40),
(6, 5, NULL, NULL, 'Primary5', 0, 'Secondary5', 0, 0, 40),
(6, 6, NULL, NULL, 'Primary6', 0, 'Secondary6', 0, 0, 40),
-- round 7
(7, 1, NULL, NULL, 'Primary1', 0, 'Secondary1', 0, 0, 40),
(7, 2, NULL, NULL, 'Primary2', 0, 'Secondary2', 0, 0, 40),
(7, 3, NULL, 	1, 'Primary3', 0, 'Secondary3', 0, 0, 40),
(7, 4, NULL, NULL, 'Primary4', 0, 'Secondary4', 0, 0, 40),
(7, 5, NULL, NULL, 'Primary5', 0, 'Secondary5', 0, 0, 40),
(7, 6, NULL, NULL, 'Primary6', 0, 'Secondary6', 0, 0, 40),
-- round 8
(8, 1, NULL, NULL, 'Primary1', 0, 'Secondary1', 0, 0, 40),
(8, 2, NULL, NULL, 'Primary2', 0, 'Secondary2', 0, 0, 40),
(8, 3, NULL, NULL, 'Primary3', 0, 'Secondary3', 0, 0, 40),
(8, 4, NULL, 	1, 'Primary4', 0, 'Secondary4', 0, 0, 40),
(8, 5, NULL, NULL, 'Primary5', 0, 'Secondary5', 0, 0, 40),
(8, 6, NULL, NULL, 'Primary6', 0, 'Secondary6', 0, 0, 40);

-- Insert 8 rounds to round_player of event 2
INSERT INTO gathering.round_player (id_round, id_player, id_player_killed_by, rank, 
primary_commander_name, primary_commander_count, secondary_commander_name, secondary_commander_count, infect_count, life_count) VALUES
-- round 1
(9, 1, NULL, NULL, 'Primary1', 0, 'Secondary1', 0, 0, 30),
(9, 2, NULL, NULL, 'Primary2', 0, 'Secondary2', 0, 0, 30),
(9, 3, NULL, NULL, 'Primary3', 0, 'Secondary3', 0, 0, 30),
(9, 4, NULL, NULL, 'Primary4', 0, 'Secondary4', 0, 0, 30),
(9, 5, NULL, 	1, 'Primary5', 0, 'Secondary5', 0, 0, 30),
(9, 6, NULL, NULL, 'Primary6', 0, 'Secondary6', 0, 0, 30),
-- round 2
(10, 1, NULL, NULL, 'Primary1', 0, 'Secondary1', 0, 0, 30),
(10, 4, NULL, NULL, 'Primary4', 0, 'Secondary4', 0, 0, 30),
(10, 5, NULL, NULL, 'Primary5', 0, 'Secondary5', 0, 0, 30),
(10, 6, NULL, NULL, 'Primary6', 0, 'Secondary6', 0, 0, 30),
(10, 7, NULL, 	 1, 'Primary2', 0, 'Secondary2', 0, 0, 30),
(10, 8, NULL, NULL, 'Primary3', 0, 'Secondary3', 0, 0, 30),
-- round 3
(11, 1, NULL, NULL, 'Primary1', 0, 'Secondary1', 0, 0, 30),
(11, 2, NULL, NULL, 'Primary2', 0, 'Secondary2', 0, 0, 30),
(11, 3, NULL, NULL, 'Primary3', 0, 'Secondary3', 0, 0, 30),
(11, 5, NULL, NULL, 'Primary5', 0, 'Secondary5', 0, 0, 30),
(11, 7, NULL, NULL, 'Primary2', 0, 'Secondary2', 0, 0, 30),
(11, 8, NULL, 	 1, 'Primary3', 0, 'Secondary3', 0, 0, 30),
-- round 4
(12, 1, NULL, 	 1, 'Primary1', 0, 'Secondary1', 0, 0, 30),
(12, 2, NULL, NULL, 'Primary2', 0, 'Secondary2', 0, 0, 30),
(12, 3, NULL, NULL, 'Primary3', 0, 'Secondary3', 0, 0, 30),
(12, 5, NULL, NULL, 'Primary5', 0, 'Secondary5', 0, 0, 30),
(12, 6, NULL, NULL, 'Primary6', 0, 'Secondary6', 0, 0, 30),
(12, 8, NULL, NULL, 'Primary6', 0, 'Secondary6', 0, 0, 30),
-- round 5
(13, 1, NULL, NULL, 'Primary1', 0, 'Secondary1', 0, 0, 30),
(13, 3, NULL, NULL, 'Primary3', 0, 'Secondary3', 0, 0, 30),
(13, 4, NULL, NULL, 'Primary4', 0, 'Secondary4', 0, 0, 30),
(13, 5, NULL, NULL, 'Primary5', 0, 'Secondary5', 0, 0, 30),
(13, 7, NULL, 	 1, 'Primary6', 0, 'Secondary6', 0, 0, 30),
(13, 8, NULL, NULL, 'Primary6', 0, 'Secondary6', 0, 0, 30),
-- round 6
(14, 1, NULL, NULL, 'Primary1', 0, 'Secondary1', 0, 0, 30),
(14, 2, NULL, NULL, 'Primary2', 0, 'Secondary2', 0, 0, 30),
(14, 3, NULL, NULL, 'Primary3', 0, 'Secondary3', 0, 0, 30),
(14, 4, NULL, NULL, 'Primary4', 0, 'Secondary4', 0, 0, 30),
(14, 5, NULL, NULL, 'Primary5', 0, 'Secondary5', 0, 0, 30),
(14, 6, NULL, 	 1, 'Primary6', 0, 'Secondary6', 0, 0, 30),
-- round 7
(15, 2, NULL, 	 1, 'Primary2', 0, 'Secondary2', 0, 0, 30),
(15, 3, NULL, NULL, 'Primary3', 0, 'Secondary3', 0, 0, 30),
(15, 4, NULL, NULL, 'Primary4', 0, 'Secondary4', 0, 0, 30),
(15, 5, NULL, NULL, 'Primary5', 0, 'Secondary5', 0, 0, 30),
(15, 6, NULL, NULL, 'Primary6', 0, 'Secondary6', 0, 0, 30),
(15, 8, NULL, NULL, 'Primary6', 0, 'Secondary6', 0, 0, 30),
-- round 8
(16, 1, NULL, NULL, 'Primary1', 0, 'Secondary1', 0, 0, 30),
(16, 4, NULL, NULL, 'Primary4', 0, 'Secondary4', 0, 0, 30),
(16, 5, NULL, NULL, 'Primary5', 0, 'Secondary5', 0, 0, 30),
(16, 6, NULL, 	 1, 'Primary6', 0, 'Secondary6', 0, 0, 30),
(16, 7, NULL, NULL, 'Primary6', 0, 'Secondary6', 0, 0, 30),
(16, 8, NULL, NULL, 'Primary6', 0, 'Secondary6', 0, 0, 30);

-- Insert 8 rounds to round_player of event 3
INSERT INTO gathering.round_player (id_round, id_player, id_player_killed_by, rank, 
primary_commander_name, primary_commander_count, secondary_commander_name, secondary_commander_count, infect_count, life_count) VALUES
-- round 1
(17, 1, NULL, 	 1, 'Primary1', 0, 'Secondary1', 0, 0, 30),
(17, 2, NULL, NULL, 'Primary2', 0, 'Secondary2', 0, 0, 30),
(17, 3, NULL, NULL, 'Primary3', 0, 'Secondary3', 0, 0, 30),
(17, 4, NULL, NULL, 'Primary4', 0, 'Secondary4', 0, 0, 30),
(17, 5, NULL, NULL, 'Primary5', 0, 'Secondary5', 0, 0, 30),
(17, 6, NULL, NULL, 'Primary6', 0, 'Secondary6', 0, 0, 30),
-- round 2
(18, 1, NULL, NULL, 'Primary1', 0, 'Secondary1', 0, 0, 30),
(18, 2, NULL, 	 1, 'Primary2', 0, 'Secondary2', 0, 0, 30),
(18, 3, NULL, NULL, 'Primary3', 0, 'Secondary3', 0, 0, 30),
(18, 4, NULL, NULL, 'Primary4', 0, 'Secondary4', 0, 0, 30),
(18, 5, NULL, NULL, 'Primary5', 0, 'Secondary5', 0, 0, 30),
(18, 6, NULL, NULL, 'Primary6', 0, 'Secondary6', 0, 0, 30),
-- round 3
(19, 1, NULL, NULL, 'Primary1', 0, 'Secondary1', 0, 0, 30),
(19, 2, NULL, NULL, 'Primary2', 0, 'Secondary2', 0, 0, 30),
(19, 3, NULL, 	 1, 'Primary3', 0, 'Secondary3', 0, 0, 30),
(19, 4, NULL, NULL, 'Primary4', 0, 'Secondary4', 0, 0, 30),
(19, 5, NULL, NULL, 'Primary5', 0, 'Secondary5', 0, 0, 30),
(19, 6, NULL, NULL, 'Primary6', 0, 'Secondary6', 0, 0, 30),
-- round 4
(20, 1, NULL, NULL, 'Primary1', 0, 'Secondary1', 0, 0, 30),
(20, 2, NULL, NULL, 'Primary2', 0, 'Secondary2', 0, 0, 30),
(20, 3, NULL, NULL, 'Primary3', 0, 'Secondary3', 0, 0, 30),
(20, 4, NULL, 	 1, 'Primary4', 0, 'Secondary4', 0, 0, 30),
(20, 5, NULL, NULL, 'Primary5', 0, 'Secondary5', 0, 0, 30),
(20, 6, NULL, NULL, 'Primary6', 0, 'Secondary6', 0, 0, 30),
-- round 5
(21, 1, NULL, NULL, 'Primary1', 0, 'Secondary1', 0, 0, 30),
(21, 2, NULL, NULL, 'Primary2', 0, 'Secondary2', 0, 0, 30),
(21, 3, NULL, NULL, 'Primary3', 0, 'Secondary3', 0, 0, 30),
(21, 4, NULL, NULL, 'Primary4', 0, 'Secondary4', 0, 0, 30),
(21, 5, NULL, 	 1, 'Primary5', 0, 'Secondary5', 0, 0, 30),
(21, 6, NULL, NULL, 'Primary6', 0, 'Secondary6', 0, 0, 30),
-- round 6
(22, 1, NULL, NULL, 'Primary1', 0, 'Secondary1', 0, 0, 30),
(22, 2, NULL, NULL, 'Primary2', 0, 'Secondary2', 0, 0, 30),
(22, 3, NULL, NULL, 'Primary3', 0, 'Secondary3', 0, 0, 30),
(22, 4, NULL, NULL, 'Primary4', 0, 'Secondary4', 0, 0, 30),
(22, 5, NULL, NULL, 'Primary5', 0, 'Secondary5', 0, 0, 30),
(22, 6, NULL, 	 1, 'Primary6', 0, 'Secondary6', 0, 0, 30);

-- Insert values in transaction
INSERT INTO gathering.transaction (id, id_player, id_gathering, id_transaction_type, created_at, amount, description) VALUES
(1, 7, 1, 1, CURRENT_TIMESTAMP, 20, NULL),
(2, 1, 1, 1, CURRENT_TIMESTAMP, 72, NULL),
(3, 2, 1, 1, CURRENT_TIMESTAMP, 20, NULL);

