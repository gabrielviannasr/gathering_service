-- Gathering confra pot total
SELECT
    g.id AS id_gathering,
    g.name AS gathering_name,
    SUM(e.confra_pot) AS total_confra_pot
FROM
    gathering.event e
    INNER JOIN gathering.gathering g ON g.id = e.id_gathering
GROUP BY
    g.id
ORDER BY
    g.name;

-- Gathering confra pot total using view (vw_gathering_confra_total)
SELECT
	id_gathering,
	gathering_name,
	total_confra_pot
FROM
	gathering.vw_gathering_confra_total
WHERE
	id_gathering = :idGathering;