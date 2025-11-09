SELECT
	id_gathering AS idGathering,
	gathering_name AS gatheringName,
	id_player AS idPlayer,
	player_name AS playerName,
	wallet
FROM
	gathering.vw_gathering_player_wallet
WHERE
	id_gathering = :idGathering
ORDER BY
	id_gathering, player_name;