package com.banking.app.bankingApp.service.accounts;

import com.banking.app.bankingApp.database.accounts.AccountsDatabaseService;
import com.banking.app.bankingApp.database.accounts.DBAccount;
import com.banking.app.bankingApp.request.accounts.CreateAccount;
import com.banking.app.bankingApp.request.accounts.UpdateAccount;
import com.banking.app.bankingApp.response.accounts.Account;
import com.banking.app.bankingApp.service.balance.BalanceManagementService;

import java.util.ArrayList;
import java.util.List;

public class AccountManagementService {
    private AccountsDatabaseService accountsDatabaseService;
    private BalanceManagementService balanceManagementService;

    public AccountManagementService() {
        accountsDatabaseService = new AccountsDatabaseService();
        balanceManagementService = new BalanceManagementService();
    }

    public Account addAccount(CreateAccount createAccount) {
        Account account = new Account();
        DBAccount dbAccount = accountsDatabaseService.createAccount(createAccount);
        account.setId(dbAccount.getId());
        account.setUserId(dbAccount.getDbUser().getId());
        account.setOwner(dbAccount.getOwner());
        account.setExpirationDate(dbAccount.getExpirationDate());
        account.setAccountName(dbAccount.getAccountName());
        account.setCreatedAt(dbAccount.getCreatedAt());
        return account;
    }

    public void updateAccount(String id, UpdateAccount updateAccount) {
        accountsDatabaseService.updateAccount(id, updateAccount);
    }

    public Account getAccountById(String id) {
        Account account = new Account();
        DBAccount dbAccount = accountsDatabaseService.findAccountById(id);
        account.setId(dbAccount.getId());
        account.setUserId(dbAccount.getDbUser().getId());
        account.setOwner(dbAccount.getOwner());
        account.setExpirationDate(dbAccount.getExpirationDate());
        account.setAccountName(dbAccount.getAccountName());
        account.setCreatedAt(dbAccount.getCreatedAt());
        account.setBalance(balanceManagementService.getBalance(id));
        return account;
    }

    public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        List<DBAccount> dbAccounts = accountsDatabaseService.getAllAccounts();
        for (int i = 0; i < dbAccounts.size(); i++) {
            Account account = new Account();
            account.setId(dbAccounts.get(i).getId());
            account.setUserId(dbAccounts.get(i).getDbUser().getId());
            account.setOwner(dbAccounts.get(i).getOwner());
            account.setExpirationDate(dbAccounts.get(i).getExpirationDate());
            account.setAccountName(dbAccounts.get(i).getAccountName());
            account.setCreatedAt(dbAccounts.get(i).getCreatedAt());
            account.setBalance(balanceManagementService.getBalance(dbAccounts.get(i).getId()));
            accounts.add(account);
        }
        return accounts;
    }

    public void deleteAccountById(String id) {
        accountsDatabaseService.deleteAccount(id);
    }
}
