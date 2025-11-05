package br.com.gathering.dto;

import br.com.gathering.entity.Result;
import jakarta.persistence.Column;
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
public class RankDTO {

	// Not needed in post method
	// private Long id;

	// private Long idEvent;

	@Column(name = "id_player")
	private Long idPlayer;
	
	@Column(name = "player_name")
	private String playerName;
	
	private Integer rank;

    private Integer wins;

    private Integer rounds;

   	private Double positive;

   	private Double negative;

   	@Column(name = "rank_balance")
    private Double rankBalance;

   	@Column(name = "loser_pot")
    private Double loserPot;


   	@Column(name = "final_balance")
    private Double finalBalance;

	public Result toModel() {
		Result rank = new Result();
	    rank.setWins(this.wins);
	    rank.setRounds(this.rounds);
	    rank.setPositive(this.positive);
	    rank.setNegative(this.negative);
	    rank.setRankBalance(this.rankBalance);
	    rank.setLoserPot(this.loserPot);
	    rank.setFinalBalance(this.finalBalance);
	    return rank;
	}

	@Override
	public String toString() {
	    return "RankDTO: {\n"
//	            + "    id: " + this.id + ",\n"
//	            + "    idEvent: " + this.idEvent + ",\n"
	            + "    idPlayer: " + this.idPlayer + ",\n"
	            + "    rank: " + this.rank + ",\n"
	            + "    wins: " + this.wins + ",\n"
	            + "    rounds: " + this.rounds + ",\n"
	            + "    positive: " + this.positive + ",\n"
	            + "    negative: " + this.negative + ",\n"
	            + "    rankBalance: " + this.rankBalance + ",\n"
	            + "    loserPot: " + this.loserPot + ",\n"
	            + "    finalBalance: " + this.finalBalance + ",\n"
	            + "}";
	}

}
