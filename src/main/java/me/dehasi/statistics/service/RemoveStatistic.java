package me.dehasi.statistics.service;

import me.dehasi.statistics.domain.Statistic;
import me.dehasi.statistics.domain.Transaction;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.ZonedDateTime;

public class RemoveStatistic implements StatisticService {
    private BigDecimal sum = BigDecimal.ZERO;
    private BigDecimal max = BigDecimal.ZERO;
    private BigDecimal min = BigDecimal.ZERO;
    private long count;

    @Override
    public synchronized Statistic getStatistic() {
        return new Statistic(sum, max, min, count);
    }

    @Override
    public synchronized void reset() {
        sum = BigDecimal.ZERO;
        max = BigDecimal.ZERO;
        min = BigDecimal.ZERO;
        count = 0;
    }

    @Override
    public void update(Transaction transaction) {
        ++count;
        sum = sum.add(transaction.amount);
        if (count == 1) {
            max = min = transaction.amount;
        } else {
            min = min.min(transaction.amount);
            max = max.max(transaction.amount);
        }

        Thread thread = new Thread(() -> {
            try {
                long wait = 60 * 1000 - (ZonedDateTime.now(Clock.systemUTC()).toInstant().toEpochMilli() - transaction.timestamp);
                Thread.sleep(wait);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            --count;
            sum = sum.add(transaction.amount.negate());
            // TODO: update min max
        });
        thread.start();
    }
}
