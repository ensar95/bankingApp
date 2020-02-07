package com.banking.app.bankingApp.controller;

import com.banking.app.bankingApp.config.TokenUtil;
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
    private TransactionManagementService transactionManagementService;
    private TokenUtil tokenUtil;

    public TransactionController() {
        transactionManagementService = TransactionManagementService.getInstance();
        tokenUtil = TokenUtil.getInstance();
    }

    @PostMapping(value = "/transactions")
    public ResponseEntity<Transaction> addTransaction(@RequestBody CreateTransaction createTransaction,
                                                      @RequestHeader(name = "Authorization") String authorization) {
        try {
            String userId=tokenUtil.getIdFromToken(authorization);
            Transaction transaction = transactionManagementService.addTransaction(createTransaction, userId);
            return ResponseEntity.status(HttpStatus.CREATED).body(transaction);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @GetMapping(value = "/transactions/{id}")
    public ResponseEntity<Transaction> getTransactionsByTransactionId(@PathVariable("id") String id,
                                                                      @RequestHeader(name = "Authorization") String authorization) {
        try {
            String userId=tokenUtil.getIdFromToken(authorization);
            Transaction transaction = transactionManagementService.getTransactionById(id,userId);
            return ResponseEntity.status(HttpStatus.OK).body(transaction);
        } catch (NoResultException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = "/transactions/{accountId}/details")
    public ResponseEntity<List<Transaction>> getTransactionsByAccountId(@PathVariable("accountId") String accountId,
                                                                        @RequestHeader(name = "Authorization") String authorization) {
        try {
            String id=tokenUtil.getIdFromToken(authorization);
            return ResponseEntity.status(HttpStatus.OK).body(transactionManagementService.getAllTransactions(accountId,id));
        } catch (NoResultException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PutMapping(value = "/transactions/{id}")
    public ResponseEntity updateTransaction(@RequestBody UpdateTransaction updateTransaction,
                                            @PathVariable("id") String id,
                                            @RequestHeader(name = "Authorization") String authorization) {
        try {
            String userId=tokenUtil.getIdFromToken(authorization);
            transactionManagementService.updateTransaction(id, updateTransaction, userId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (NoResultException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @DeleteMapping(value = "/transactions/{id}")
    public ResponseEntity deleteTransaction(@PathVariable("id") String id,
                                            @RequestHeader(name = "Authorization") String authorization) {
        try {
            String userId=tokenUtil.getIdFromToken(authorization);
            transactionManagementService.deleteTransaction(id,userId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (NoResultException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}
