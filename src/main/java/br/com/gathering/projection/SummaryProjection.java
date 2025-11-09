package br.com.gathering.projection;

public interface SummaryProjection {
	Long getIdGathering();
	String getGatheringName();
	Integer getPlayers();
	Integer getRounds();
	Double getLoserPot();
	Double getConfraPot();
	Double getPrize();
}
