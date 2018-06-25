package me.dehasi.statistics.domain;

import java.math.BigDecimal;

public final class Transaction {
    public final BigDecimal amount;
    public final long timestamp;

    public Transaction(BigDecimal amount, long timestamp) {
        this.amount = amount;
        this.timestamp = timestamp;
    }
}
