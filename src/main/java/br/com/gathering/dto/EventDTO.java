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

//	Not needed in post method
//	private Long id;

	private Long idFormat;

	private LocalDateTime createdAt;

    private Double registrationFee;

    private Integer players;

    private Integer rounds;

    private Double confraFee5;

    private Double confraFee6;

    private Double confraPot;

    private Double loserFee5;

    private Double loserFee6;

    private Double loserPot;

    public Event toEvent() {
        Event event = new Event();
//        event.setId(this.id);
        event.setIdFormat(this.idFormat);
        event.setCreatedAt(this.createdAt);
        event.setRegistrationFee(this.registrationFee);
        event.setPlayers(this.players);
        event.setRounds(this.rounds);
        event.setConfraFee5(this.confraFee5);
        event.setConfraFee6(this.confraFee6);
        event.setConfraPot(this.confraPot);
        event.setLoserFee5(this.loserFee5);
        event.setLoserFee6(this.loserFee6);
        event.setLoserPot(this.loserPot);

        return event;
    }

	@Override
	public String toString() {
	    return "EventDTO: {\n"
//	            + "    id: " + this.id + ",\n"
	            + "    idFormat: " + this.idFormat + ",\n"
	            + "    createdAt: " + this.createdAt + ",\n"
	            + "    registrationFee: " + this.registrationFee + ",\n"
	            + "    players: " + this.players + ",\n"
	            + "    rounds: " + this.rounds + ",\n"
	            + "    confraFee5: " + this.confraFee5 + ",\n"
	            + "    confraFee6: " + this.confraFee6 + ",\n"
	            + "    confraPot: " + this.confraPot + ",\n"
	            + "    loserFee5: " + this.loserFee5 + ",\n"
	            + "    loserFee6: " + this.loserFee6 + ",\n"
	            + "    loserPot: " + this.loserPot + ",\n"
	            + "}";
	}

}
