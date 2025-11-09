package br.com.gathering.projection.event;

public interface EventSummaryProjection {
	Long getIdGathering();
	String getGatheringName();
	Long getIdEvent();
	Integer getPlayers();
	Integer getRounds();
	Double getLoserPot();
	Double getConfraPot();
	Double getPrize();	
}
