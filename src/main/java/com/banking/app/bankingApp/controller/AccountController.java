package com.banking.app.bankingApp.controller;

import com.banking.app.bankingApp.config.TokenUtil;
import com.banking.app.bankingApp.request.accounts.CreateAccount;
import com.banking.app.bankingApp.request.accounts.UpdateAccount;
import com.banking.app.bankingApp.response.accounts.Account;
import com.banking.app.bankingApp.service.accounts.AccountManagementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NoResultException;
import java.util.List;

@RestController
public class AccountController {
    private AccountManagementService accountManagementService;
    private TokenUtil tokenUtil;

    public AccountController() {
        accountManagementService = AccountManagementService.getInstance();
        tokenUtil = TokenUtil.getInstance();
    }

    @PostMapping(value = "/accounts")
    public ResponseEntity<Account> addAccount(@RequestBody CreateAccount createAccount,
                                              @RequestHeader(name = "Authorization") String authorization) {
        try {
            String userId = tokenUtil.getIdFromToken(authorization);
            Account account = accountManagementService.addAccount(createAccount, userId);
            return ResponseEntity.status(HttpStatus.CREATED).body(account);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PutMapping(value = "/accounts/{id}")
    public ResponseEntity updateAccount(@RequestBody UpdateAccount updateAccount,
                                        @PathVariable("id") String id,
                                        @RequestHeader(name = "Authorization") String authorization) {
        try {
            String userId = tokenUtil.getIdFromToken(authorization);
            accountManagementService.updateAccount(id, updateAccount, userId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (NoResultException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @GetMapping(value = "/accounts/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable("id") String id,
                                                  @RequestHeader(name = "Authorization") String authorization
    ) {
        try {
            String userId = tokenUtil.getIdFromToken(authorization);
            Account account = accountManagementService.getAccountById(id, userId);
            return ResponseEntity.status(HttpStatus.OK).body(account);
        } catch (NoResultException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @GetMapping(value = "/accounts")
    public ResponseEntity<List<Account>> getAllAccounts(
            @RequestHeader(name = "Authorization") String authorization
    ) {
        try {
            String userId = tokenUtil.getIdFromToken(authorization);
            return ResponseEntity.status(HttpStatus.OK).body(accountManagementService.getAllAccounts(userId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @DeleteMapping(value = "/accounts/{id}")
    public ResponseEntity deleteAccount(@PathVariable("id") String id,
                                        @RequestHeader(name = "Authorization") String authorization) {
        try {
            String userId = tokenUtil.getIdFromToken(authorization);
            accountManagementService.deleteAccountById(id, userId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (NoResultException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}
