package com.banking.app.bankingApp.controller;

import com.banking.app.bankingApp.request.transactions.CreateTransaction;
import com.banking.app.bankingApp.request.transactions.UpdateTransaction;
import com.banking.app.bankingApp.response.transactions.Transaction;
import com.banking.app.bankingApp.service.transactions.TransactionManagementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NoResultException;
import java.util.List;

@RestController
public class TransactionController {
    TransactionManagementService transactionManagementService;

    public TransactionController() {
        transactionManagementService = new TransactionManagementService();
    }

    @PostMapping(value = "/transactions")
    public ResponseEntity<Transaction> addTransaction(@RequestBody CreateTransaction createTransaction) {
        Transaction transaction = transactionManagementService.addTransaction(createTransaction);
        return ResponseEntity.status(HttpStatus.OK).body(transaction);
    }

    @GetMapping(value = "/transactions/{id}")
    public ResponseEntity<Transaction> getTransaction(@PathVariable("id") String id) {
        try {
            Transaction transaction = transactionManagementService.getTransactionById(id);
            return ResponseEntity.status(HttpStatus.OK).body(transaction);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = "/transactions")
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(transactionManagementService.getAllTransactions());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping(value = "/transactions/{id}")
    public ResponseEntity updateTransaction(@RequestBody UpdateTransaction updateTransaction, @PathVariable("id") String id) {
        try {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (NoResultException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping(value = "/transactions/{id}")
    public ResponseEntity deleteTransaction(@PathVariable("id") String id) {
        try {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (NoResultException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
