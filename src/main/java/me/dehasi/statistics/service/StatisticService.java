package me.dehasi.statistics.service;

import me.dehasi.statistics.domain.Statistic;

public interface StatisticService {
    Statistic getStatistic();

    void reset();

    void update();
}
