CREATE OR REPLACE VIEW gathering.vw_gathering_format AS
    SELECT
        g.id AS id_gathering,
        g.name AS gathering_name,
        f.id AS id_format,
        f.name AS format_name,
        COUNT(r.id) AS rounds
    FROM
        gathering.gathering g
        INNER JOIN gathering.event e ON e.id_gathering = g.id
        INNER JOIN gathering.round r ON r.id_event = e.id
        INNER JOIN gathering.format f ON r.id_format = f.id
    WHERE
        r.canceled = false
    GROUP BY
        g.id, f.id
    ORDER BY
        g.name, f.name;

    COMMENT ON VIEW gathering.vw_gathering_format IS
    'Displays the total number rounds for each format played in the gathering.';