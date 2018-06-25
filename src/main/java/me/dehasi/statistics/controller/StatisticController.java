package me.dehasi.statistics.controller;

import me.dehasi.statistics.domain.Statistic;
import me.dehasi.statistics.service.StatisticService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatisticController {
    private final StatisticService service;

    public StatisticController(StatisticService service) {
        this.service = service;
    }

    @GetMapping("statistics")
    Statistic getStatistic() {
        return service.getStatistic();
    }

    @Scheduled(fixedDelay = 60 * 1000)
    public void resetStatistic() {
        service.reset();
    }

}
