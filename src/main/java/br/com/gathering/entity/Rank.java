package br.com.gathering.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
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
@Entity
@Table(name = "rank", schema = "gathering")
public class Rank {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gathering.sequence_rank")
	@SequenceGenerator(name = "gathering.sequence_rank", sequenceName = "gathering.sequence_rank", allocationSize = 1)
	private Long id;

	@Column(name = "id_event", nullable = false)
	private Long idEvent;

	@ManyToOne
	@JoinColumn(name = "id_event", nullable = true, insertable = false, updatable = false)
	private Event event;

	@Column(name = "id_player", nullable = true)
	private Long idPlayer;

	@ManyToOne
	@JoinColumn(name = "id_player", nullable = true, insertable = false, updatable = false)
	private Player player;	

	@Column(nullable = false)
	private Integer rank;

	@Column(nullable = false)
    private Integer wins;

   @Column(nullable = false)
    private Integer rounds;

   @Column(name = "positive", nullable = false)
   	private Double positive;

   @Column(name = "negative", nullable = false)
   	private Double negative;

	@Column(name = "rank_balance", nullable = false)
    private Double rankBalance;

	@Column(name = "loser_pot", nullable = false)
    private Double loserPot;

	@Column(name = "prize_taken", nullable = false)
    private Double prizeTaken;

	@Column(name = "final_balance", nullable = false)
    private Double finalBalance;

	public void init() {
	    this.wins = (this.wins == null) ? 0: this.wins;
	    this.rounds = (this.rounds == null) ? 0 : this.rounds;
	    this.positive = (this.positive == null) ? 0 : this.positive;
	    this.negative = (this.negative == null) ? 0 : this.negative;
	    this.rankBalance = (this.rankBalance == null) ? 0: this.rankBalance;
	    this.loserPot = (this.loserPot == null) ? 0 : this.loserPot;
	    this.prizeTaken = (this.prizeTaken == null) ? 0 : this.prizeTaken;
	    this.finalBalance = (this.finalBalance == null) ? 0 : this.finalBalance;
	}

	@Override
	public String toString() {
	    return "Rank: {\n"
	            + "    id: " + this.id + ",\n"
	            + "    idEvent: " + this.idEvent + ",\n"
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
