CREATE OR REPLACE VIEW gathering.vw_event_summary AS
SELECT 
    loser.id_gathering,
    loser.gathering_name,
    loser.id_event,
    confra.players,
    loser.rounds,
    loser.loser_pot,
    confra.confra_pot,
    loser.prize
FROM
    gathering.vw_event_loser_pot loser
    INNER JOIN gathering.vw_event_confra_pot confra
        ON confra.id_event = loser.id_event
ORDER BY
	loser.id_gathering, loser.id_event;

COMMENT ON VIEW gathering.vw_event_summary IS
'Apresenta um resumo consolidado de cada evento, unindo informações do pote da confra e do pote dos derrotados.
Inclui o total de jogadores, rodadas, valores acumulados e premiações do evento.';