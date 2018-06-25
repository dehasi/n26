package me.dehasi.statistics.controller;

import me.dehasi.statistics.domain.Transaction;
import me.dehasi.statistics.service.StatisticService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Clock;
import java.time.Instant;
import java.time.ZonedDateTime;

@RestController
public class TransactionController {

    private final StatisticService service;

    public TransactionController(StatisticService service) {
        this.service = service;
    }

    @PostMapping("transactions")
    ResponseEntity<?> submitTransaction(@RequestBody Transaction transaction) {
        if (ZonedDateTime.now(Clock.systemUTC()).toInstant().minusSeconds(60).isAfter(Instant.ofEpochMilli(transaction.timestamp))) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        service.update(transaction);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
