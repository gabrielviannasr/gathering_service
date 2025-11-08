package br.com.gathering.dto;

import java.time.LocalDateTime;

import br.com.gathering.entity.Event;
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
public class EventDTO {

	// Not needed in post method
	// private Long id;

	private Long idGathering;

	private Long idFormat;

	private LocalDateTime createdAt;

    private Integer players;

    private Integer rounds;

    private Double confraFee;

    private Double roundFee;

    private Double loserFee4;

    private Double loserFee5;

    private Double loserFee6;

    private Double confraPot;

    private Double loserPot;

    private Double prize;

    public Event toModel() {
        Event event = new Event();
//        event.setId(this.id);
        event.setIdGathering(this.idGathering);
        event.setIdFormat(this.idFormat);
        event.setCreatedAt(this.createdAt);
        event.setPlayers(this.players);
        event.setRounds(this.rounds);
        event.setConfraFee(this.confraFee);
        event.setRoundFee(this.roundFee);
        event.setLoserFee5(this.loserFee4);
        event.setLoserFee5(this.loserFee5);
        event.setLoserFee6(this.loserFee6);
        event.setLoserPot(this.loserPot);
        event.setConfraPot(this.confraPot);
        event.setPrize(this.prize);

        return event;
    }

	@Override
	public String toString() {
	    return "Event: {\n"
//	            + "\tid: " + this.id + ",\n"
	            + "\tidGathering: " + this.idGathering + ",\n"
	            + "\tidFormat: " + this.idFormat + ",\n"
	            + "\tcreatedAt: " + this.createdAt + ",\n"
	            + "\tplayers: " + this.players + ",\n"
	            + "\trounds: " + this.rounds + ",\n"
	            + "\tconfraFee: " + this.confraFee + ",\n"
	            + "\troundFee: " + this.roundFee + ",\n"
	            + "\tloserFee4: " + this.loserFee4 + ",\n"
	            + "\tloserFee5: " + this.loserFee5 + ",\n"
	            + "\tloserFee6: " + this.loserFee6 + ",\n"
	            + "\tloserPot: " + this.loserPot + ",\n"
	            + "\tconfraPot: " + this.confraPot + ",\n"
	            + "\tprize: " + this.prize + ",\n"
	            + "}";
	}

}
