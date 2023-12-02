package br.com.gabrielviannasr.gathering.entity;

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
@Table(name = "player", schema = "gathering")
public class Player {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "player_sequence")
	@SequenceGenerator(name = "player_sequence", sequenceName = "player_sequence", allocationSize = 1)
	private Long id;

	@Column(nullable = false)
    private String name;

    private String username;

    private String email;

    private String password;

    @Builder.Default
    private Double wallet = 0.0;

    @Override
    public String toString() {
		return "Player: {\n"
				+ "	id: " + this.id + ",\n"
				+ "	name: " + this.name + ",\n"
				+ "	username: " + this.username + ",\n"
				+ "	password: " + this.password + ",\n"
				+ "	wallet: " + this.wallet + ",\n"
				+ "}";
    }

}
