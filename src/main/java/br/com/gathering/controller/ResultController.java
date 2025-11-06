package br.com.gathering.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gathering.entity.Result;
import br.com.gathering.projection.ConfraPotProjection;
import br.com.gathering.projection.LoserPotProjection;
import br.com.gathering.projection.RankCountProjection;
import br.com.gathering.projection.RankProjection;
import br.com.gathering.service.ResultService;

@RestController
@RequestMapping("/result")
public class ResultController {

    @Autowired
    private ResultService service;

    @GetMapping("/{idEvent}")
    public List<Result> getResult(@PathVariable Long idEvent) {
    	System.out.println("idEvent: " + idEvent);
        return service.getResult(idEvent);
    }

//    @PostMapping("/{idEvent}")
//    public List<Result> saveResult(@PathVariable Long idEvent) {
//    	System.out.println("idEvent: " + idEvent);
//        return service.saveResult(idEvent);
//    }

    @GetMapping("/{idEvent}/confra-pot")
    public ConfraPotProjection getConfraPot(@PathVariable Long idEvent) {
    	System.out.println("idEvent: " + idEvent);
        return service.getConfraPot(idEvent);
    }

    @GetMapping("/{idEvent}/loser-pot")
    public LoserPotProjection getLoserPot(@PathVariable Long idEvent) {
    	System.out.println("idEvent: " + idEvent);
        return service.getLoserPot(idEvent);
    }

    @GetMapping("/{idEvent}/rank-count")
    public List<RankCountProjection> getRankCount(@PathVariable Long idEvent) {
    	System.out.println("idEvent: " + idEvent);
        return service.getRankCount(idEvent);
    }

    @GetMapping("/{idEvent}/rank")
    public List<RankProjection> getRankProjection(@PathVariable Long idEvent) {
    	System.out.println("idEvent: " + idEvent);
        return service.getRankProjection(idEvent);
    }
}
