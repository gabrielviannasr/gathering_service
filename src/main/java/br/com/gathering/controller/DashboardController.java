package br.com.gathering.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gathering.projection.event.RankProjection;
import br.com.gathering.projection.gathering.FormatProjection;
import br.com.gathering.projection.gathering.PlayerTransactionProjection;
import br.com.gathering.projection.gathering.PlayerWalletProjection;
import br.com.gathering.service.DashboardService;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService service;

    @GetMapping("/{idGathering}/wallet-balance")
    public List<PlayerWalletProjection> getWalletBalance(@PathVariable Long idGathering) {
    	System.out.println("idGathering: " + idGathering);
        return service.getWalletBalance(idGathering);
    }

    @GetMapping("/{idGathering}/transaction")
    public List<PlayerTransactionProjection> getPlayerTransaciton(@PathVariable Long idGathering) {
    	System.out.println("idGathering: " + idGathering);
        return service.getPlayerTransaciton(idGathering);
    }

    @GetMapping("/{idGathering}/format")
    public List<FormatProjection> getFormatProjection(@PathVariable Long idGathering) {
    	System.out.println("idGathering: " + idGathering);
        return service.getFormatProjection(idGathering);
    }

    @GetMapping("/{idGathering}/rank")
    public List<RankProjection> getRankProjection(@PathVariable Long idGathering) {
    	System.out.println("idGathering: " + idGathering);
        return service.getRankProjection(idGathering);
    }

}
