package com.banking.app.bankingApp.service.accounts;

import com.banking.app.bankingApp.database.accounts.AccountsDatabaseService;
import com.banking.app.bankingApp.database.accounts.DBAccount;
import com.banking.app.bankingApp.request.accounts.CreateAccount;
import com.banking.app.bankingApp.request.accounts.UpdateAccount;
import com.banking.app.bankingApp.response.accounts.Account;

import java.util.ArrayList;
import java.util.List;

public class AccountManagementService {
    private AccountsDatabaseService accountsDatabaseService;

    public AccountManagementService() {
        accountsDatabaseService = new AccountsDatabaseService();
    }

    public Account addAccount(CreateAccount createAccount) {
        Account account = new Account();
        DBAccount dbAccount = new DBAccount();
        dbAccount = accountsDatabaseService.createDbAccount(createAccount);
        account.setId(dbAccount.getId());
        account.setUserId(dbAccount.getUserId());
        account.setOwner(dbAccount.getOwner());
        account.setExpirationDate(dbAccount.getExpirationDate());
        account.setAccountName(dbAccount.getAccountName());
        account.setCreatedAt(dbAccount.getCreatedAt());
        return account;
    }

    public void updateAccount(String id, UpdateAccount updateAccount) {
        accountsDatabaseService.updateDBAccount(id, updateAccount);
    }

    public Account getAccountById(String id) {
        Account account = new Account();
        DBAccount dbAccount = accountsDatabaseService.findAccountById(id);
        account.setId(dbAccount.getId());
        account.setUserId(dbAccount.getUserId());
        account.setOwner(dbAccount.getOwner());
        account.setExpirationDate(dbAccount.getExpirationDate());
        account.setAccountName(dbAccount.getAccountName());
        account.setCreatedAt(dbAccount.getCreatedAt());
        return account;
    }

    public List<Account> getAllAc() {
        List<Account> accounts = new ArrayList<>();
        List<DBAccount> dbAccounts = accountsDatabaseService.getAllAcc();
        for (int i = 0; i < dbAccounts.size(); i++) {
            Account account = new Account();
            account.setId(dbAccounts.get(i).getId());
            account.setUserId(dbAccounts.get(i).getUserId());
            account.setOwner(dbAccounts.get(i).getOwner());
            account.setExpirationDate(dbAccounts.get(i).getExpirationDate());
            account.setAccountName(dbAccounts.get(i).getAccountName());
            account.setCreatedAt(dbAccounts.get(i).getCreatedAt());
            accounts.add(account);
        }
        return accounts;
    }

    public void deleteAccountById(String id) {
        accountsDatabaseService.deleteDBAccount(id);
    }
}
