package br.com.gathering.projection.gathering;

import java.time.LocalDateTime;

public interface PlayerTransactionProjection {
//	Long getIdGathering();
//	String getGatheringName();
	Long getIdPlayer();
	String getPlayerName();
	Long getIdTransaction();
	LocalDateTime getCreatedAt();
	String getTransactionTypeName();
	Double getAmount();
	String getTransactionDescription();
}
