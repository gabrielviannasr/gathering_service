package br.com.gathering.projection.gathering;

public interface SummaryProjection {
	Integer getEvents();
	Integer getRounds();
	Double getLoserPot();
	Double getConfraPot();
	Double getPrize();
}
