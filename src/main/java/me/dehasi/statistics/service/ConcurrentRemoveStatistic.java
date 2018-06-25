package me.dehasi.statistics.service;

import me.dehasi.statistics.domain.Statistic;
import me.dehasi.statistics.domain.Transaction;
import org.springframework.stereotype.Service;

@Service
public class ConcurrentRemoveStatistic implements StatisticService {
    private Statistic statistic = new Statistic();

    @Override
    public synchronized Statistic getStatistic() {
        return statistic.getStatistic();
    }

    @Override
    public synchronized void reset() {
        statistic.reset();
    }

    @Override
    public void update(Transaction transaction) {
        statistic.update(transaction);
    }
}
