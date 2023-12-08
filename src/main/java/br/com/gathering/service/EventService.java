package br.com.gathering.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.gathering.entity.Event;
import br.com.gathering.entity.Rank;
import br.com.gathering.projection.PotProjection;
import br.com.gathering.projection.RankCountProjection;
import br.com.gathering.repository.EventRepository;

@Service
public class EventService extends AbstractService<Event> {

	private static final double WORST_RANK_LOSER_POT_PERCENTAGE = 0.6;
	private static final double SECOND_WORST_RANK_LOSER_POT_PERCENTAGE = 0.4;

	@Autowired
	private EventRepository repository;

	public static Sort getSort() {
		return Sort.by(Order.asc("createdAt"));
	}

	public List<Event> getList(Event model) {
		return repository.findAll(getExample(model), getSort());
	}

	public Event getById(Long id) {
		Optional<Event> optional = repository.findById(id);
		return optional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}
	
	public PotProjection getPot(Long idEvent) {
		PotProjection pot = repository.getPot(idEvent);
		System.out.println("pot: " + pot);
		return pot;
	}

	public List<RankCountProjection> getRankCount(Long idEvent) {
		List<RankCountProjection> list = repository.getRankCount(idEvent);
		list.forEach(item -> System.out.println(item));
		return list;
	}

//	public List<RankProjection> getRank(Long idEvent) {
//		return repository.getRank(idEvent);
//	}

	public List<Rank> getRank(Long idEvent) {
		// Get loserPot and confraPot
		PotProjection pot = repository.getPot(idEvent);
		// Get rank count to determine how many players will share the loserPot
		List<RankCountProjection> rankCount = getRankCount(idEvent);
		// Get rank list to update the loserPot and finalBalance
		List<Rank> list = repository.getRank(idEvent);
		// 100% of loserPot divided equally between the worst ranked players
		if (rankCount.get(0).getCount() > 1) {
			// 100% equally divided
			Double percentage = 1.0 / rankCount.get(0).getCount();
			// Loop to update loserPot and finalBalance
			list.forEach(item -> {
				// Non-worst players takes 0% of loserPot
				Double loserPot = 0.0;
				// If player rank is the (1st) worst rank, then updates finalBalance
				if (item.getRank() == rankCount.get(0).getRank() ) {
					loserPot = percentage * pot.getLoserPot();
					item.setFinalBalance(item.getFinalBalance() + loserPot);
				}
				// Update loserPot
				item.setLoserPot(loserPot);
				// Update idEvent
				item.setIdEvent(idEvent);
			});
		}
		// 60% of loserPot to the worst ranked player, and 40% divided equally between the second worst ranked players
		else {
			// 40% equally divided
			Double percentage = SECOND_WORST_RANK_LOSER_POT_PERCENTAGE / rankCount.get(1).getCount();
			// Loop to update loserPot and finalBalance
			list.forEach(item -> {
				// Non-worst players takes 0% of loserPot
				Double loserPot = 0.0;
				// If player rank is the (1st) worst rank, then updates finalBalance
				if (item.getRank() == rankCount.get(0).getRank() ) {
					loserPot = WORST_RANK_LOSER_POT_PERCENTAGE * pot.getLoserPot();
					item.setFinalBalance(item.getFinalBalance() + loserPot);
				}
				// If player rank is the 2nd worst rank, then updates finalBalance
				else if (item.getRank() == rankCount.get(1).getRank() ) {
					loserPot = percentage * pot.getLoserPot();
					item.setFinalBalance(item.getFinalBalance() + loserPot);
				}
				// Update loserPot
				item.setLoserPot(loserPot);
				// Update idEvent
				item.setIdEvent(idEvent);
			});

		}
		// Log updated items
		list.forEach(item -> {
			System.out.println(item);
		});
		// Return the updated list
		return list;
	}

	public Event save(Event model) {
		model.init();
		return repository.save(model);
	}

}
