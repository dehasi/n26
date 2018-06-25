package me.dehasi.statistics.domain;

import java.math.BigDecimal;

public final class Statistic {
    public final BigDecimal sum;
    public final BigDecimal avg;
    public final BigDecimal max;
    public final BigDecimal min;
    public final long count;

    public Statistic(BigDecimal sum, BigDecimal max, BigDecimal min, long count) {
        this.sum = sum;
        this.avg = count > 0 ? sum.divide(BigDecimal.valueOf(count), 2) : BigDecimal.ZERO;
        this.max = max;
        this.min = min;
        this.count = count;
    }
}
