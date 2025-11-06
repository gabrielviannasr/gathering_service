package br.com.gathering.entity;

import java.time.LocalDateTime;

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
@Table(name = "event", schema = "gathering")
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gathering.sequence_event")
	@SequenceGenerator(name = "gathering.sequence_event", sequenceName = "gathering.sequence_event", allocationSize = 1)
	private Long id;

	@Column(name = "id_gathering", nullable = false)
	private Long idGathering;

	@ManyToOne
	@JoinColumn(name = "id_gathering", nullable = true, insertable = false, updatable = false)
	private Gathering gathering;

	@Column(name = "id_format", nullable = false)
	private Long idFormat;

	@ManyToOne
	@JoinColumn(name = "id_format", nullable = true, insertable = false, updatable = false)
	private Format format;

	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt;

	@Column(nullable = false)
    private Integer players;

    @Column(nullable = false)
    private Integer rounds;

	@Column(name = "confra_fee", nullable = false)
    private Double confraFee;

	@Column(name = "round_fee", nullable = false)
    private Double roundFee;

	@Column(name = "loser_fee4", nullable = false)
    private Double loserFee4;

	@Column(name = "loser_fee5", nullable = false)
    private Double loserFee5;

	@Column(name = "loser_fee6", nullable = false)
    private Double loserFee6;

	@Column(name = "loser_pot", nullable = false)
    private Double loserPot;

	@Column(name = "confra_pot", nullable = false)
    private Double confraPot;

//	@OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
//	private List<Rank> ranks;

	public void init() {
	    this.createdAt = (this.createdAt == null) ? LocalDateTime.now() : this.createdAt;
	    this.players = (this.players == null) ? 0 : this.players;
	    this.rounds = (this.rounds == null) ? 0 : this.rounds;
	    this.confraFee = (this.confraFee == null) ? 0.0 : this.confraFee;
	    this.roundFee = (this.roundFee == null) ? 0.0 : this.roundFee;
	    this.loserFee4 = (this.loserFee4 == null) ? 0.0 : this.loserFee4;
	    this.loserFee5 = (this.loserFee5 == null) ? 0.0 : this.loserFee5;
	    this.loserFee6 = (this.loserFee6 == null) ? 0.0 : this.loserFee6;    
	    this.loserPot = (this.loserPot == null) ? 0.0 : this.loserPot;
	    this.confraPot = (this.confraPot == null) ? 0.0 : this.confraPot;
	}

	@Override
	public String toString() {
	    return "Event: {\n"
	            + "    id: " + this.id + ",\n"
	            + "    idGathering: " + this.idGathering + ",\n"
	            + "    idFormat: " + this.idFormat + ",\n"
	            + "    createdAt: " + this.createdAt + ",\n"
	            + "    players: " + this.players + ",\n"
	            + "    rounds: " + this.rounds + ",\n"
	            + "    confraFee: " + this.confraFee + ",\n"
	            + "    roundFee: " + this.roundFee + ",\n"
	            + "    loserFee4: " + this.loserFee4 + ",\n"
	            + "    loserFee5: " + this.loserFee5 + ",\n"
	            + "    loserFee6: " + this.loserFee6 + ",\n"
	            + "    loserPot: " + this.loserPot + ",\n"
	            + "    confraPot: " + this.confraPot + ",\n"
	            + "}";
	}

}
