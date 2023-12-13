package br.com.gathering.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NamedNativeQuery(
	name = "getRank",
	query = ""
			+ "SELECT\r\n"
			+ "    RANK() OVER (ORDER BY wins DESC, rounds) AS rank,\r\n"
			+ "    id_player AS idPlayer,\r\n"
			+ "    username,\r\n"
			+ "    wins,\r\n"
			+ "    rounds,\r\n"
			+ "    positive,\r\n"
			+ "    -negative AS negative,\r\n"
			+ "    positive - negative AS rankBalance,\r\n"
			+ "    -prize_taken AS prizeTaken,\r\n"
			+ "    positive - negative - prize_taken AS finalBalance\r\n"
			+ "FROM (\r\n"
			+ "    SELECT\r\n"
			+ "        p.id AS id_player,\r\n"
			+ "        p.username,\r\n"
			+ "        COUNT(CASE WHEN rp.rank = 1 THEN 1 END) AS wins,\r\n"
			+ "        COUNT(rp.id_player) AS rounds,\r\n"
			+ "        COUNT(CASE WHEN rp.rank = 1 THEN 1 END) * e.prize AS positive,\r\n"
			+ "        COUNT(rp.id_player) * e.registration_fee AS negative,\r\n"
			+ "        SUM (CASE WHEN rp.rank = 1 THEN r.prize_taken ELSE 0 END) AS prize_taken\r\n"
			+ "    FROM\r\n"
			+ "        gathering.round_player rp\r\n"
			+ "        INNER JOIN gathering.round r ON r.id = rp.id_round\r\n"
			+ "        INNER JOIN gathering.event e ON e.id = r.id_event\r\n"
			+ "        INNER JOIN gathering.player p ON p.id = rp.id_player\r\n"
			+ "    WHERE\r\n"
			+ "        e.id = :idEvent\r\n"
			+ "        AND r.canceled = false\r\n"
			+ "    GROUP BY\r\n"
			+ "        p.id, p.username, e.registration_fee, e.prize\r\n"
			+ ") AS subquery\r\n"
			+ "ORDER BY\r\n"
			+ "    rank, username;",
	resultSetMapping = "Rank"
)
@SqlResultSetMapping(
    name = "Rank",
    classes = @ConstructorResult(
        targetClass = Rank.class,
        columns = {
    		@ColumnResult(name = "rank", type = Integer.class),
    		@ColumnResult(name = "idPlayer", type = Long.class),
            @ColumnResult(name = "wins", type = Integer.class),
            @ColumnResult(name = "rounds", type = Integer.class),
            @ColumnResult(name = "positive", type = Double.class),
            @ColumnResult(name = "negative", type = Double.class),
            @ColumnResult(name = "rankBalance", type = Double.class),
            @ColumnResult(name = "prizeTaken", type = Double.class),
            @ColumnResult(name = "finalBalance", type = Double.class)
        }
    )
)
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

	@JsonIgnore
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
