package br.com.gathering.projection;

public interface RankProjection {
	Integer getRank();
	Long getIdPlayer();
    String getName();
    Integer getWins();
    Integer getRounds();
    Double getPositive();
    Double getNegative();
    Double getRankBalance();
    Double getLoserPot();
    Double getFinalBalance();
}
