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
@Table(name = "format", schema = "gathering")
public class Format {

	public static final int NAME_LENGTH = 20;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gathering.sequence_format")
	@SequenceGenerator(name = "gathering.sequence_format", sequenceName = "gathering.sequence_format", allocationSize = 1)
	private Long id;

	@Column(nullable = false, length = NAME_LENGTH)
    private String name;

	@Column(name = "life_count", nullable = false)
    private Integer lifeCount;

	public void init() {
		this.lifeCount = (this.lifeCount == null) ? 0 : this.lifeCount;
	}

    @Override
    public String toString() {
		return "Format: {\n"
				+ "\tid: " + this.id + ",\n"
				+ "\tname: " + this.name + ",\n"
				+ "\tlifeCount: " + this.lifeCount + ",\n"
				+ "}";
    }

}
