package me.dehasi.statistics.domain;

import java.math.BigDecimal;

public final class Statistic {
    public final BigDecimal sum;
    public final BigDecimal avg;
    public final BigDecimal max;
    public final BigDecimal min;
    public final long count;

    public Statistic(BigDecimal sum, BigDecimal avg, BigDecimal max, BigDecimal min, long count) {
        this.sum = sum;
        this.avg = avg;
        this.max = max;
        this.min = min;
        this.count = count;
    }
}
