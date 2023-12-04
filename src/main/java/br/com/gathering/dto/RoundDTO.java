package br.com.gathering.dto;

import java.time.LocalDateTime;

import br.com.gathering.entity.Round;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoundDTO {

//	Not needed in post method
//	private Long id;

	private Long idEvent;

	private Long idFormat;

	private Long idPlayerWinner;	

	private LocalDateTime createdAt;

    private Integer round;

    private Integer players;

    private Integer boostersPrize;

    private Boolean canceled;

    public Round toModel() {
        Round round = new Round();
//        round.setId(this.id);
        round.setIdEvent(this.idEvent);
        round.setIdFormat(this.idFormat);
        round.setIdPlayerWinner(this.idPlayerWinner);
        round.setCreatedAt(this.createdAt);
        round.setRound(this.round);
        round.setPlayers(this.players);
        round.setBoostersPrize(this.boostersPrize);
        round.setCanceled(this.canceled);

        return round;
    }
    
	@Override
	public String toString() {
	    return "RoundDTO: {\n"
//	            + "    id: " + this.id + ",\n"
	            + "    idEvent: " + this.idEvent + ",\n"
	            + "    idFormat: " + this.idFormat + ",\n"
	            + "    idPlayerWinner: " + this.idPlayerWinner + ",\n"
	            + "    createdAt: " + this.createdAt + ",\n"
	            + "    round: " + this.round + ",\n"
	            + "    players: " + this.players + ",\n"
	            + "    boostersPrize: " + this.boostersPrize + ",\n"
	            + "    canceled: " + this.canceled + ",\n"
	            + "}";
	}

}
