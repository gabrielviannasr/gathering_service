package br.com.gathering.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
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

	public static final int EMAIL_LENGTH = 50;
	public static final int NAME_LENGTH = 50;
	public static final int PASSWORD_LENGTH = 20;
	public static final int USERNAME_LENGTH = 20;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gathering.sequence_player")
	@SequenceGenerator(name = "gathering.sequence_player", sequenceName = "gathering.sequence_player", allocationSize = 1)
	private Long id;

	@Column(nullable = false, length = NAME_LENGTH)
    private String name;

	@Column(length = USERNAME_LENGTH)
    private String username;

	@Column(length = EMAIL_LENGTH)
    private String email;

	@Transient
	@JsonIgnore
	@Column(length = PASSWORD_LENGTH)
    private String password;

    @Column(nullable = false)
    private Double wallet;
    
	public void init() {
	    this.wallet = (this.wallet == null) ? 0.0 : this.wallet;
	}

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
