package me.dehasi.statistics.controller;

import me.dehasi.statistics.domain.Statistic;
import me.dehasi.statistics.service.StatisticService;
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

}
