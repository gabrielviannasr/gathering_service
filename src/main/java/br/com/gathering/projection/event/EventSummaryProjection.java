package br.com.gathering.projection.event;

import br.com.gathering.projection.SummaryProjection;

public interface EventSummaryProjection extends SummaryProjection {
	Long getIdEvent();
}
