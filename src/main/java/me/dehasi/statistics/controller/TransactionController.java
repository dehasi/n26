package me.dehasi.statistics.controller;

import me.dehasi.statistics.domain.Transaction;
import me.dehasi.statistics.service.StatisticService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

    private final StatisticService service;

    public TransactionController(StatisticService service) {
        this.service = service;
    }

    @PostMapping("transactions")
    ResponseEntity<?> submitTranaction(@RequestBody Transaction transaction) {
        service.update(transaction);
        return ResponseEntity.noContent().build();
    }
}
