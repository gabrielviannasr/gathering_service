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
		PotProjection pot = repository.getPot(idEvent);
		
		List<RankCountProjection> rankCount = getRankCount(idEvent);
		
		List<Rank> list = repository.getRank(idEvent);
//		100% of loserPot divided equally between the worst ranked players
		if (rankCount.get(0).getCount() > 1) {
			Double percetage = 1.0 / rankCount.get(0).getCount();

			list.forEach(item -> {
				Double loserPot = 0.0;

				if (item.getRank() == rankCount.get(0).getRank() ) {
					loserPot = percetage * pot.getLoserPot();
					item.setFinalBalance(item.getFinalBalance() + loserPot);
				} 
				item.setLoserPot(loserPot);
				item.setIdEvent(idEvent);
				System.out.println(item);
			});
		}
//		60% of loserPot to the worst ranked player, and 40% divided equally between the second worst ranked players
		else {
			Double percetage = 0.4 / rankCount.get(1).getCount();

			list.forEach(item -> {
				Double loserPot = 0.0;

				if (item.getRank() == rankCount.get(0).getRank() ) {
					loserPot = 0.6 * pot.getLoserPot();
					item.setFinalBalance(item.getFinalBalance() + loserPot);
				} else if (item.getRank() == rankCount.get(1).getRank() ) {
					loserPot = percetage * pot.getLoserPot();
					item.setFinalBalance(item.getFinalBalance() + loserPot);
				}
				item.setLoserPot(loserPot);
				item.setIdEvent(idEvent);
				System.out.println(item);
			});
			
		}
		
		list.forEach(item -> {
			System.out.println(item);
		});
		return list;
	}

	public Event save(Event model) {
		model.init();
		return repository.save(model);
	}

}
