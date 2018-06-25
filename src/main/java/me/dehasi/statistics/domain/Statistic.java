package me.dehasi.statistics.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.ZonedDateTime;

public final class Statistic {
    public BigDecimal sum;
    public BigDecimal avg;
    public BigDecimal max;
    public BigDecimal min;
    public long count;

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
            min = min.min(transaction.amount);
            max = max.max(transaction.amount);
        }


        Thread thread = new Thread(() -> {
            try {
                long wait = 60 * 1000 - (ZonedDateTime.now(Clock.systemUTC()).toInstant().toEpochMilli() - transaction.timestamp);
                Thread.sleep(wait);

                synchronized (this) {
                    --count;
                    sum = sum.add(transaction.amount.negate());
                    // TODO: update min max
                }
            } catch (InterruptedException e) {
            }
        });
        thread.start();
    }
}
