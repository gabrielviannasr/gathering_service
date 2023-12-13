package br.com.gathering.projection;

public interface DashboardProjection {
    Long getIdPlayer();
    String getUsername();
    Double getEventsBalance();
    Double getTransactionsBalance();
    Double getFinalBalance();
}
