package me.dehasi.statistics.service;

import me.dehasi.statistics.domain.Statistic;
import me.dehasi.statistics.domain.Transaction;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class DummySynchronizedStatisticTest {

    private static final BigDecimal AMOUNT_1_42 = new BigDecimal("1.42");
    private static final BigDecimal AMOUNT_5_58 = new BigDecimal("5.58");
    private static final Transaction TRANSACTION = new Transaction(BigDecimal.TEN, 42L);
    private StatisticService service;

    @Before
    public void createService() {
        service = new DummySynchronizedStatistic();
    }

    @Test
    public void getStatistic_byDefault_returnsZeros() {
        Statistic statistic = service.getStatistic();

        assertThat(statistic.sum).isEqualTo(BigDecimal.ZERO);
        assertThat(statistic.avg).isEqualTo(BigDecimal.ZERO);
        assertThat(statistic.max).isEqualTo(BigDecimal.ZERO);
        assertThat(statistic.min).isEqualTo(BigDecimal.ZERO);
        assertThat(statistic.count).isEqualTo(0L);
    }

    @Test
    public void getStatistic_firstSubmit_initializeFields() {
        service.update(TRANSACTION);

        Statistic statistic = service.getStatistic();

        assertThat(statistic.sum).isEqualTo(TRANSACTION.amount);
        assertThat(statistic.avg).isEqualTo(TRANSACTION.amount);
        assertThat(statistic.max).isEqualTo(TRANSACTION.amount);
        assertThat(statistic.min).isEqualTo(TRANSACTION.amount);
        assertThat(statistic.count).isEqualTo(1L);
    }

    @Test
    public void getStatistic_fewUpdates_returnsCorrectStatistic() {
        service.update(new Transaction(BigDecimal.TEN, 42L));
        service.update(new Transaction(AMOUNT_1_42, 42L));
        service.update(new Transaction(AMOUNT_5_58, 42L));

        Statistic statistic = service.getStatistic();

        assertThat(statistic.sum).isEqualTo(new BigDecimal("17.00"));
        assertThat(statistic.avg).isEqualTo(new BigDecimal("5.67"));
        assertThat(statistic.max).isEqualTo(BigDecimal.TEN);
        assertThat(statistic.min).isEqualTo(new BigDecimal("1.42"));
        assertThat(statistic.count).isEqualTo(3L);
    }


}
