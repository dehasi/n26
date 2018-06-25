package me.dehasi.statistics.domain;

import org.junit.Test;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class StatisticsTest {

    private Statistic statistic = new Statistic();

    @Test
    public void getStatistic_byDefault_returnsZeros() {
        Statistic statistic = this.statistic.getStatistic();

        assertThat(statistic.sum).isEqualTo(BigDecimal.ZERO);
        assertThat(statistic.avg).isEqualTo(BigDecimal.ZERO);
        assertThat(statistic.max).isEqualTo(BigDecimal.ZERO);
        assertThat(statistic.min).isEqualTo(BigDecimal.ZERO);
        assertThat(statistic.count).isEqualTo(0L);
    }

    @Test
    public void update_updatesStatistic() {
        Transaction transaction = new Transaction(BigDecimal.TEN, ZonedDateTime.now(Clock.systemUTC()).toInstant().toEpochMilli());

        statistic.update(transaction);

        Statistic statistic = this.statistic.getStatistic();

        assertThat(statistic.sum).isEqualTo(BigDecimal.TEN);
        assertThat(statistic.avg).isEqualTo(BigDecimal.TEN);
        assertThat(statistic.max).isEqualTo(BigDecimal.TEN);
        assertThat(statistic.min).isEqualTo(BigDecimal.TEN);
        assertThat(statistic.count).isEqualTo(1L);
    }

    @Test
    public void update_afterMinute_removesTransaction() {
        Transaction transaction = new Transaction(BigDecimal.TEN, ZonedDateTime.now(Clock.systemUTC()).toInstant().toEpochMilli());

        statistic.update(transaction);


        sleep(30 * 1000);
        Statistic statisticBefore = statistic.getStatistic();

        assertThat(statisticBefore.sum).isEqualTo(BigDecimal.TEN);
        assertThat(statisticBefore.avg).isEqualTo(BigDecimal.TEN);
        assertThat(statisticBefore.max).isEqualTo(BigDecimal.TEN);
        assertThat(statisticBefore.min).isEqualTo(BigDecimal.TEN);
        assertThat(statisticBefore.count).isEqualTo(1L);

        sleep(30 * 1000);
        Statistic statisticAfter = statistic.getStatistic();

        assertThat(statisticAfter.sum).isEqualTo(BigDecimal.ZERO);
        assertThat(statisticAfter.avg).isEqualTo(BigDecimal.ZERO);
        assertThat(statisticAfter.max).isEqualTo(BigDecimal.ZERO);
        assertThat(statisticAfter.min).isEqualTo(BigDecimal.ZERO);
        assertThat(statisticAfter.count).isEqualTo(0L);
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
