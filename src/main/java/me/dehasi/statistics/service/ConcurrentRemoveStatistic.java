package me.dehasi.statistics.service;

import me.dehasi.statistics.domain.Statistic;
import me.dehasi.statistics.domain.Transaction;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.ZonedDateTime;

public class ConcurrentRemoveStatistic implements StatisticService {
    private Statistic statistic = new Statistic();

    @Override
    public synchronized Statistic getStatistic() {
        return statistic;
    }

    @Override
    public synchronized void reset() {
        statistic.sum = statistic.avg = statistic.min = statistic.max = BigDecimal.ZERO;
        statistic.count = 0;
    }

    @Override
    public void update(Transaction transaction) {
        synchronized (statistic) {
            ++statistic.count;
            statistic.sum = statistic.sum.add(transaction.amount);
            if (statistic.count == 1) {
                statistic.max = statistic.min = transaction.amount;
            } else {
                statistic.min = statistic.min.min(transaction.amount);
                statistic.max = statistic.max.max(transaction.amount);
            }

        }
        Thread thread = new Thread(() -> {
            try {
                long wait = 60 * 1000 - (ZonedDateTime.now(Clock.systemUTC()).toInstant().toEpochMilli() - transaction.timestamp);
                Thread.sleep(wait);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (statistic) {
                --statistic.count;
                statistic.sum = statistic.sum.add(transaction.amount.negate());
                // TODO: update min max
            }
        });
        thread.start();
    }
}
