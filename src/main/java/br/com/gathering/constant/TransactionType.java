package br.com.gathering.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransactionType {

    INSCRICAO (1L, "Inscrição", "Taxa destinada à confra"),
    RESULTADO (2L, "Resultado", "Saldo final do evento"),
    DEPOSITO (3L, "Depósito", "Pagamento de inscrições e resultados"),
    SAQUE (4L, "Saque", "Recebimento de premiação");
	
	private final Long id;
	private final String name;
	private final String description;

}