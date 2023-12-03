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
@Table(name = "round", schema = "gathering")
public class Round {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gathering.sequence_round")
	@SequenceGenerator(name = "gathering.sequence_round", sequenceName = "gathering.sequence_round", allocationSize = 1)
	private Long id;

	@Column(name = "id_event", nullable = false)
	private Long idEvent;

	@ManyToOne
	@JoinColumn(name = "id_event", nullable = true, insertable = false, updatable = false)
	private Event event;

	@Column(name = "id_format", nullable = false)
	private Long idFormat;

	@ManyToOne
	@JoinColumn(name = "id_format", nullable = true, insertable = false, updatable = false)
	private Format format;

	@Column(name = "id_player_winner", nullable = true)
	private Long idPlayerWinner;

	@ManyToOne
	@JoinColumn(name = "id_player_winner", nullable = true, insertable = false, updatable = false)
	private Player playerWinner;	

	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt;

   @Column(nullable = false)
    private Integer round;

	@Column(nullable = false)
    private Integer players;

	@Column(name = "boosters_prize", nullable = false)
    private Integer boostersPrize;

	@Column(nullable = false)
    private Boolean canceled;

	public void init() {
	    this.createdAt = (this.createdAt == null) ? LocalDateTime.now() : this.createdAt;
	    this.players = (this.players == null) ? 0 : this.players;
	    this.boostersPrize = (this.boostersPrize == null) ? 0 : this.boostersPrize;
	    this.canceled = (this.canceled == null) ? false : this.canceled;
	}

	@Override
	public String toString() {
	    return "Round: {\n"
	            + "    id: " + this.id + ",\n"	            
	            + "    idEvent: " + this.idEvent + ",\n"
	            + "    idFormat: " + this.idFormat + ",\n"
	            + "    idPlayerWinner: " + this.idPlayerWinner + ",\n"
	            + "    createdAt: " + this.createdAt + ",\n"
	            + "    round: " + this.round + ",\n"
	            + "    players: " + this.players + ",\n"
	            + "    boostersPrize: " + this.boostersPrize + ",\n"
	            + "    canceled: " + this.canceled + ",\n"
	            + "}";
	}

}
