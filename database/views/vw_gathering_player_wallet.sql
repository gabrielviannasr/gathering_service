CREATE OR REPLACE VIEW gathering.vw_gathering_player_wallet AS
SELECT
    g.id AS id_gathering,
    g.name AS gathering_name,
    p.id AS id_player,
    p.name AS player_name,
    COALESCE(SUM(t.amount), 0) AS wallet
FROM
    gathering.player p
LEFT JOIN
    gathering.transaction t ON t.id_player = p.id
LEFT JOIN
    gathering.gathering g ON g.id = t.id_gathering
GROUP BY
    g.id, g.name, p.id, p.name
ORDER BY
    g.name, p.name;

COMMENT ON VIEW gathering.vw_gathering_player_wallet IS
'Displays each player''s wallet (balance) grouped by gathering, based on all related transactions.';