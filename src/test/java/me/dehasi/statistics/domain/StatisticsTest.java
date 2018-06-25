package me.dehasi.statistics.domain;

import com.google.common.collect.MinMaxPriorityQueue;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Comparator;

import static org.assertj.core.api.Assertions.assertThat;

public class StatisticsTest {
    MinMaxPriorityQueue<BigDecimal> minQueue = MinMaxPriorityQueue
            .orderedBy(BigDecimal::compareTo)
            .expectedSize(10000)
            .create();

    MinMaxPriorityQueue<BigDecimal> maxQueue = MinMaxPriorityQueue
            .orderedBy((Comparator<BigDecimal>) (o1, o2) -> -1 * o1.compareTo(o2))
            .expectedSize(10000)
            .create();

    @Test
    public void queTest() {
        minQueue.add(BigDecimal.ONE);
        minQueue.add(BigDecimal.ZERO);
        minQueue.add(BigDecimal.TEN);

        assertThat(minQueue.peekFirst()).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    public void queTest1() {
        minQueue.add(BigDecimal.ONE);
        minQueue.add(BigDecimal.ZERO);
        minQueue.add(BigDecimal.TEN);
        minQueue.remove(BigDecimal.ZERO);

        assertThat(minQueue.peekFirst()).isEqualTo(BigDecimal.ONE);
    }

    @Test
    public void setMaxQueueTest() {
        maxQueue.add(BigDecimal.ONE);
        maxQueue.add(BigDecimal.ZERO);
        maxQueue.add(BigDecimal.TEN);

        assertThat(maxQueue.peekFirst()).isEqualTo(BigDecimal.TEN);
    }
}
