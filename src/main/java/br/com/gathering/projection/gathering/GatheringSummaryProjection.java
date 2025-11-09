package br.com.gathering.projection.gathering;

public interface GatheringSummaryProjection {
	Long getIdGathering();
	String getGatheringName();
	Integer getEvents();
	Integer getPlayers();
	Integer getRounds();
	Double getLoserPot();
	Double getConfraPot();
	Double getPrize();	
}
