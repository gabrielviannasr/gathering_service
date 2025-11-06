-- Insert transaction_type table initial load
INSERT INTO gathering.transaction_type (id, name, description) VALUES
(1, 'Inscrição', 'Taxa destinada à confra'),
(2, 'Resultado', 'Saldo final do evento')
(3, 'Depósito', 'Pagamento de inscrições e resultados'),
(4, 'Saque', 'Recebimento de premiação');
ON CONFLICT (id) DO NOTHING; -- Avoid duplicate if run twice

-- Insert format table initial load
INSERT INTO gathering.format (id, name, life_count) VALUES
(1, 'Commander', 40),
(2, 'Conquest', 30),
(3, 'Tiny Leader', 30)
ON CONFLICT (id) DO NOTHING; -- Avoid duplicate if run twice