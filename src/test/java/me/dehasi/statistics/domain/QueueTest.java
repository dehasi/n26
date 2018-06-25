package me.dehasi.statistics.domain;

import com.google.common.collect.MinMaxPriorityQueue;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Comparator;

import static org.assertj.core.api.Assertions.assertThat;

public class QueueTest {
    private MinMaxPriorityQueue<BigDecimal> minQueue = MinMaxPriorityQueue
            .orderedBy(BigDecimal::compareTo)
            .expectedSize(100)
            .create();

    private MinMaxPriorityQueue<BigDecimal> maxQueue = MinMaxPriorityQueue
            .orderedBy((Comparator<BigDecimal>) (o1, o2) -> -1 * o1.compareTo(o2))
            .expectedSize(100)
            .create();

    @Test
    public void minQueue_hasAppropriateComparator() {
        minQueue.add(BigDecimal.ONE);
        minQueue.add(BigDecimal.ZERO);
        minQueue.add(BigDecimal.TEN);

        assertThat(minQueue.peekFirst()).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    public void minQueue_removesElement() {
        minQueue.add(BigDecimal.ONE);
        minQueue.add(BigDecimal.ZERO);
        minQueue.add(BigDecimal.TEN);
        minQueue.remove(BigDecimal.ZERO);

        assertThat(minQueue.peekFirst()).isEqualTo(BigDecimal.ONE);
    }

    @Test
    public void maxQueue_hasAppropriateComparator() {
        maxQueue.add(BigDecimal.ONE);
        maxQueue.add(BigDecimal.ZERO);
        maxQueue.add(BigDecimal.TEN);

        assertThat(maxQueue.peekFirst()).isEqualTo(BigDecimal.TEN);
    }

    @Test
    public void maxQueue_containsMultipleValues() {
        maxQueue.add(BigDecimal.TEN);
        maxQueue.add(BigDecimal.TEN);
        maxQueue.add(BigDecimal.TEN);

        assertThat(maxQueue.size()).isEqualTo(3);
    }
}
