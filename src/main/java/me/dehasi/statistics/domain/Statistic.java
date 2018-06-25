package me.dehasi.statistics.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.MinMaxPriorityQueue;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.Comparator;

public final class Statistic {
    public BigDecimal sum;
    public BigDecimal avg;
    public BigDecimal max;
    public BigDecimal min;
    public long count;

    MinMaxPriorityQueue<BigDecimal> minQueue = MinMaxPriorityQueue
            .orderedBy(BigDecimal::compareTo)
            .expectedSize(10000)
            .create();

    MinMaxPriorityQueue<BigDecimal> maxQueue = MinMaxPriorityQueue
            .orderedBy((Comparator<BigDecimal>) (o1, o2) -> -1 * o1.compareTo(o2))
            .expectedSize(10000)
            .create();


    public Statistic(BigDecimal sum, BigDecimal max, BigDecimal min, long count) {
        this.sum = sum;
        this.avg = count > 0 ? sum.divide(BigDecimal.valueOf(count), 2) : BigDecimal.ZERO;
        this.max = max;
        this.min = min;
        this.count = count;
    }

    public Statistic() {
        sum = BigDecimal.ZERO;
        avg = BigDecimal.ZERO;
        max = BigDecimal.ZERO;
        min = BigDecimal.ZERO;
        count = 0;
    }

    @JsonIgnore
    public synchronized Statistic getStatistic() {
        this.avg = count > 0 ? sum.divide(BigDecimal.valueOf(count), 2) : BigDecimal.ZERO;
        min = minQueue.size() > 0 ? minQueue.peekFirst() : BigDecimal.ZERO;
        max = maxQueue.size() > 0 ? maxQueue.peekFirst() : BigDecimal.ZERO;
        return this;
    }

    @JsonIgnore
    public synchronized void reset() {
        sum = avg = min = max = BigDecimal.ZERO;
        count = 0;
    }

    @JsonIgnore
    public synchronized void update(Transaction transaction) {
        ++count;
        sum = sum.add(transaction.amount);
        if (count == 1) {
            max = min = transaction.amount;
        } else {
            minQueue.add(transaction.amount);
            maxQueue.add(transaction.amount);
        }


        cleanUp(transaction);
    }

    private void cleanUp(Transaction transaction) {
        Thread thread = new Thread(() -> {
            try {
                long wait = 60 * 1000 - (ZonedDateTime.now(Clock.systemUTC()).toInstant().toEpochMilli() - transaction.timestamp);
                Thread.sleep(wait);

                synchronized (this) {
                    --count;
                    sum = sum.add(transaction.amount.negate());
                    minQueue.remove(transaction.amount);
                    maxQueue.remove(transaction.amount);

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }
}
