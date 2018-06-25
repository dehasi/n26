package me.dehasi.statistics.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public final class Transaction {
    public final BigDecimal amount;
    public final long timestamp;

    @JsonCreator
    public Transaction(@JsonProperty("amount") BigDecimal amount, @JsonProperty("timestamp") long timestamp) {
        this.amount = amount;
        this.timestamp = timestamp;
    }
}
