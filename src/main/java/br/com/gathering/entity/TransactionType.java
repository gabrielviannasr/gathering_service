package br.com.gathering.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "transaction_type", schema = "gathering")
public class TransactionType {

	public static final int NAME_LENGTH = 50;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gathering.sequence_transaction_type")
	@SequenceGenerator(name = "gathering.sequence_transaction_type", sequenceName = "gathering.sequence_transaction_type", allocationSize = 1)
	private Long id;

	@Column(length = NAME_LENGTH)
    private String name;

	public void init() {
	}

	@Override
    public String toString() {
		return "TransactionType: {\n"
				+ "\tid: " + this.id + ",\n"
				+ "\tname: " + this.name + ",\n"
				+ "}";
    }
}
