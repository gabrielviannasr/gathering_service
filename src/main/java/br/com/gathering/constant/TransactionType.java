package br.com.gathering.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransactionType {
	
	DEPOSITO (1L, "Depósito", "Pagamento de inscrições e resultados"),
	SAQUE (2L, "Saque", "Recebimento de premiação"),
	INSCRICAO (3L, "Inscrição", "Taxa destinada à confra"),
	RESULTADO (4L, "Resultado", "Saldo final do evento");
	
	private final Long id;
	private final String name;
	private final String description;

}