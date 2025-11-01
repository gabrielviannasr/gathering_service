INSERT INTO gathering.player (id, name, wallet) VALUES
(1, 'Anderson Dias', 0),
(2, 'Arthur Leal', 0),
(3, 'Cindomar Ferreira', 0),
(4, 'Gabriel Vianna', 0),
(5, 'Jean Benevides', 0),
(6, 'Jhonny Dias', 0),
(7, 'Tobias Souza', 0),
(8, 'Valmir Vicente', 0);

INSERT INTO gathering.gathering (id, id_owner, name, year) VALUES
(1, 1, 'DIRETORIA', 2025);

START TRANSACTION;



ROLLBACK;