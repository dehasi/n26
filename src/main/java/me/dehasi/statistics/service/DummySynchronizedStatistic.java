package me.dehasi.statistics.service;

import me.dehasi.statistics.domain.Statistic;
import me.dehasi.statistics.domain.Transaction;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class DummySynchronizedStatistic implements StatisticService {

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
    public synchronized void update(Transaction transaction) {
        ++count;
        sum = sum.add(transaction.amount);
        if (count == 1) {
            max = min = transaction.amount;
        } else {
            min = min.min(transaction.amount);
            max = max.max(transaction.amount);
        }
    }
}
