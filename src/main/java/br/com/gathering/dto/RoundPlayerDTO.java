package br.com.gathering.dto;

import br.com.gathering.entity.RoundPlayer;
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
public class RoundPlayerDTO {

//	Not needed in post method
//	private Long id;

	private Long idRound;

	private Long idPlayer;

	private Long idPlayerKilledBy;

    private Integer rank;

    private String primaryCommanderName;

    private Integer primaryCommanderCount;

    private String secondaryCommanderName;

    private Integer secondaryCommanderCount;

    private Integer infectCount;

    private Integer lifeCount;
    
    public RoundPlayer toRoundPlayer() {
        RoundPlayer roundPlayer = new RoundPlayer();
//        roundPlayer.setId(this.id);
        roundPlayer.setIdRound(this.idRound);
        roundPlayer.setIdPlayer(this.idPlayer);
        roundPlayer.setIdPlayerKilledBy(this.idPlayerKilledBy);
        roundPlayer.setRank(this.rank);
        roundPlayer.setPrimaryCommanderName(this.primaryCommanderName);
        roundPlayer.setPrimaryCommanderCount(this.primaryCommanderCount);
        roundPlayer.setSecondaryCommanderName(this.secondaryCommanderName);
        roundPlayer.setSecondaryCommanderCount(this.secondaryCommanderCount);
        roundPlayer.setInfectCount(this.infectCount);
        roundPlayer.setLifeCount(this.lifeCount);
       
        return roundPlayer;
    }

	@Override
	public String toString() {
	    return "RoundPlayerDTO: {\n"
//	            + "    id: " + this.id + ",\n"
	            + "    idRound: " + this.idRound + ",\n"
	            + "    idPlayer: " + this.idPlayer + ",\n"
	            + "    idPlayerKilledBy: " + this.idPlayerKilledBy + ",\n"
	            + "    rank: " + this.rank + ",\n"
	            + "    primaryCommanderName: " + this.primaryCommanderName + ",\n"
	            + "    primaryCommanderCount: " + this.primaryCommanderCount + ",\n"
	            + "    secondaryCommanderName: " + this.secondaryCommanderName + ",\n"
	            + "    secondaryCommanderCount: " + this.secondaryCommanderCount + ",\n"
	            + "    infectCount: " + this.infectCount + ",\n"
	            + "    lifeCount: " + this.lifeCount + ",\n"
	            + "}";
	}

}
