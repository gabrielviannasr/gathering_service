package br.com.gathering.projection;

public interface RankProjection {
	Long getIdPlayer();
    String getPlayerName();
	Integer getRank();
    Integer getWins();
    Integer getRounds();
    Double getPositive();
    Double getNegative();
    Double getRankBalance();
}
