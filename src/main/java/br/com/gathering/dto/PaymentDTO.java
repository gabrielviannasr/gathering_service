package br.com.gathering.dto;

import java.time.LocalDateTime;

import br.com.gathering.entity.Payment;
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
public class PaymentDTO {

	public static final int DESCRIPTION_LENGTH = 20;

//	Not needed in post method
//	private Long id;

	private Long idPlayer;

	private LocalDateTime createdAt;

	private Double invoice;

    private String description;

    public Payment toModel() {
    	Payment payment = new Payment();
    	payment.setIdPlayer(this.idPlayer);
    	payment.setCreatedAt(this.createdAt);
    	payment.setInvoice(this.invoice);
    	payment.setDescription(this.description);

    	return payment;    	
    }

	@Override
    public String toString() {
		return "Format: {\n"
//				+ "\tid: " + this.id + ",\n"
				+ "\tidPlayer: " + this.idPlayer + ",\n"
				+ "\tcreatedAt: " + this.createdAt + ",\n"
				+ "\tinvoice: " + this.invoice + ",\n"
				+ "\tdescription: " + this.description + ",\n"
				+ "}";
    }
}
