package com.banking.app.bankingApp.controller;

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
    AccountManagementService accountManagementService;

    public AccountController() {
        accountManagementService = new AccountManagementService();
    }

    @PostMapping(value = "/accounts")
    public ResponseEntity<Account> addAccount(@RequestBody CreateAccount createAccount) {
        try {
            Account account = accountManagementService.addAccount(createAccount);
            return ResponseEntity.status(HttpStatus.OK).body(account);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping(value = "/accounts/{id}")
    public ResponseEntity updateAccount(@RequestBody UpdateAccount updateAccount, @PathVariable("id") String id) {
        try {
            accountManagementService.updateAccount(id, updateAccount);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (NoResultException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping(value = "/accounts/{id}")
    public ResponseEntity<Account> getAccById(@PathVariable("id") String id) {
        try {
            Account account = accountManagementService.getAccountById(id);
            return ResponseEntity.status(HttpStatus.OK).body(account);
        } catch (NoResultException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping(value = "/accounts")
    public ResponseEntity<List<Account>> getAllAccounts() {
        return ResponseEntity.status(HttpStatus.OK).body(accountManagementService.getAllAc());
    }

    @DeleteMapping(value = "/accounts/{id}")
    public ResponseEntity deleteAccount(@PathVariable("id") String id) {
        try {
            accountManagementService.deleteAccountById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (NoResultException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
