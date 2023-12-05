package br.com.gathering.projection;

public interface RankProjection {
    Long getIdPlayer();
    String getUsername();
    Integer getRank();
    Integer getWins();
    Integer getRounds();
    Double getPositive();
    Double getNegative();
    Double getRankBalance();
    Double getLoserPot();
    Double getPrizeTaken();
    Double getFinalBalance();
}
