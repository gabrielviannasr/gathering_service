-- Insert transaction_type table initial load
INSERT INTO gathering.transaction_type (id, name) VALUES
(1, 'Balance'),              -- Final value of the player in the event (positive = won / negative = owed)
(2, 'Payment'),              -- When a player pays off a debt
(3, 'Withdrawal')            -- When a player withdraws/receives a prize
ON CONFLICT (id) DO NOTHING; -- avoid duplicate if run twice

-- Insert format table initial load
INSERT INTO gathering.format (id, name, life_count) VALUES
(1, 'Commander', 40),
(2, 'Conquest', 30),
(3, 'Tiny Leader', 30)
ON CONFLICT (id) DO NOTHING; -- avoid duplicate if run twice