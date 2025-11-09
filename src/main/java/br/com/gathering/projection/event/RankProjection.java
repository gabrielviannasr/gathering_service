package br.com.gathering.projection.event;

public interface RankProjection {
	Integer getRank();
	Long getIdPlayer();
    String getPlayerName();
    Integer getWins();
    Integer getRounds();
    Double getPositive();
    Double getNegative();
    Double getRankBalance();
}
