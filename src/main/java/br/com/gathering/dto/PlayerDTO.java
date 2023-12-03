package br.com.gathering.dto;

import br.com.gathering.entity.Player;
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
public class PlayerDTO {

//	Not needed in post method
//	private Long id;

    private String name;

    private String username;

    private String email;

    private String password;

    private Double wallet;
    
	public void init() {
	    this.wallet = (this.wallet == null) ? 0.0 : this.wallet;
	}
	
	public Player toPlayer() {
	    Player player = new Player();
//	    player.setId(this.id);
	    player.setName(this.name);
	    player.setUsername(this.username);
	    player.setEmail(this.email);
	    player.setPassword(this.password);
	    player.setWallet(this.wallet);

	    return player;
	}

    @Override
    public String toString() {
		return "PlayerDTO: {\n"
//				+ "	id: " + this.id + ",\n"
				+ "	name: " + this.name + ",\n"
				+ "	username: " + this.username + ",\n"
				+ "	password: " + this.password + ",\n"
				+ "	wallet: " + this.wallet + ",\n"
				+ "}";
    }

}
