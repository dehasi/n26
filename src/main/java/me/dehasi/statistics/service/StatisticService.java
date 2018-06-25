package me.dehasi.statistics.service;

import me.dehasi.statistics.domain.Statistic;
import me.dehasi.statistics.domain.Transaction;

public interface StatisticService {
    Statistic getStatistic();

    void reset();

    void update(Transaction transaction);
}
