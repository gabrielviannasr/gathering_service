package br.com.gathering.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

	@Column(name = "id_format", nullable = false)
	private Long idFormat;

	@ManyToOne
	@JoinColumn(name = "id_format", nullable = true, insertable = false, updatable = false)
	private Format format;

	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt;

	@Column(name = "registration_fee", nullable = false)
    private Double registrationFee;

	@Column(nullable = false)
    private Integer players;

    @Column(nullable = false)
    private Integer rounds;

	@Column(name = "confra_fee5", nullable = false)
    private Double confraFee5;

	@Column(name = "confra_fee6", nullable = false)
    private Double confraFee6;

	@Column(name = "confra_pot", nullable = false)
    private Double confraPot;

	@Column(name = "loser_fee5", nullable = false)
    private Double loserFee5;

	@Column(name = "loser_fee6", nullable = false)
    private Double loserFee6;

	@Column(name = "loser_pot", nullable = false)
    private Double loserPot;

	@OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Rank> ranks;

	public void init() {
	    this.createdAt = (this.createdAt == null) ? LocalDateTime.now() : this.createdAt;
	    this.registrationFee = (this.registrationFee == null) ? 0.0 : this.registrationFee;
	    this.players = (this.players == null) ? 0 : this.players;
	    this.rounds = (this.rounds == null) ? 0 : this.rounds;
	    this.confraFee5 = (this.confraFee5 == null) ? 0.0 : this.confraFee5;
	    this.confraFee6 = (this.confraFee6 == null) ? 0.0 : this.confraFee6;
	    this.confraPot = (this.confraPot == null) ? 0.0 : this.confraPot;
	    this.loserFee5 = (this.loserFee5 == null) ? 0.0 : this.loserFee5;
	    this.loserFee6 = (this.loserFee6 == null) ? 0.0 : this.loserFee6;
	    this.loserPot = (this.loserPot == null) ? 0.0 : this.loserPot;
	}

	@Override
	public String toString() {
	    return "Event: {\n"
	            + "    id: " + this.id + ",\n"
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
