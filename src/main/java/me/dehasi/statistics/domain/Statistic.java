package me.dehasi.statistics.domain;

import java.math.BigDecimal;

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
}
