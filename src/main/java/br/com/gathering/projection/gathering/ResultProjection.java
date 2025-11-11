package br.com.gathering.projection.gathering;

import br.com.gathering.projection.RankProjection;

public interface ResultProjection extends RankProjection {
	Long getIdGathering();
	String getGatheringName();
	Integer getEvents();
	Double getLoserPot();
    Double getConfraPot();
    Double getFinalBalance();
}
