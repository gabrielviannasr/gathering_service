package br.com.gathering.dto;

import br.com.gathering.entity.Rank;
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

//	private Long id;

//	private Long idEvent;

	@Column(name = "id_player")
	private Long idPlayer;
	
	private String username;
	
	private Integer rank;

    private Integer wins;

    private Integer rounds;

   	private Double positive;

   	private Double negative;

   	@Column(name = "rank_balance")
    private Double rankBalance;

   	@Column(name = "loser_pot")
    private Double loserPot;

   	@Column(name = "prize_taken")
    private Double prizeTaken;

   	@Column(name = "final_balance")
    private Double finalBalance;

	public Rank toModel() {
		Rank rank = new Rank();
	    rank.setWins(this.wins);
	    rank.setRounds(this.rounds);
	    rank.setPositive(this.positive);
	    rank.setNegative(this.negative);
	    rank.setRankBalance(this.rankBalance);
	    rank.setLoserPot(this.loserPot);
	    rank.setPrizeTaken(this.prizeTaken);
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
	            + "    prizeTaken: " + this.prizeTaken + ",\n"
	            + "    finalBalance: " + this.finalBalance + ",\n"
	            + "}";
	}

}
