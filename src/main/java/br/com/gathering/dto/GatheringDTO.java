package br.com.gathering.dto;

import br.com.gathering.entity.Gathering;
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
public class GatheringDTO {
	
	// Not needed in post method
	// private Long id;

	private Long idPlayer;

	private Integer year;

    private String name;
    
	public void init() {
	}

	public Gathering toModel() {
		Gathering gathering = new Gathering();
		gathering.setIdPlayer(this.idPlayer);
		gathering.setYear(this.year);
		gathering.setName(this.name);

		return gathering;
	}
	
    @Override
    public String toString() {
		return "GatheringDTO: {\n"
				// + "\tid: " + this.id + ",\n"
				+ "\tidPlayer: " + this.idPlayer + ",\n"
				+ "\tyear: " + this.year + ",\n"
				+ "\tname: " + this.name + ",\n"
				+ "}";
    }

}
