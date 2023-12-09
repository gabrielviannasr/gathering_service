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
@Table(name = "payment", schema = "gathering")
public class Payment {

	public static final int DESCRIPTION_LENGTH = 20;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gathering.sequence_payment")
	@SequenceGenerator(name = "gathering.sequence_payment", sequenceName = "gathering.sequence_payment", allocationSize = 1)
	private Long id;

	@Column(name = "id_player", nullable = false)
	private Long idPlayer;

	@ManyToOne
	@JoinColumn(name = "id_player", nullable = true, insertable = false, updatable = false)
	private Player player;

	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt;

	@Column(name = "invoice", nullable = false)
	private Double invoice;

	@Column(length = DESCRIPTION_LENGTH)
    private String description;

	public void init() {
	    this.createdAt = (this.createdAt == null) ? LocalDateTime.now() : this.createdAt;
	    this.invoice = (this.invoice == null) ? 0 : this.invoice;
	}

	@Override
    public String toString() {
		return "Format: {\n"
				+ "\tid: " + this.id + ",\n"
				+ "\tidPlayer: " + this.idPlayer + ",\n"
				+ "\tcreatedAt: " + this.createdAt + ",\n"
				+ "\tinvoice: " + this.invoice + ",\n"
				+ "\tdescription: " + this.description + ",\n"
				+ "}";
    }
}
