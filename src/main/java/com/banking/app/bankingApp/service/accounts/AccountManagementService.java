package com.banking.app.bankingApp.service.accounts;

import com.banking.app.bankingApp.config.TokenUtil;
import com.banking.app.bankingApp.database.accounts.AccountsDatabaseService;
import com.banking.app.bankingApp.database.accounts.DBAccount;
import com.banking.app.bankingApp.request.accounts.CreateAccount;
import com.banking.app.bankingApp.request.accounts.UpdateAccount;
import com.banking.app.bankingApp.response.accounts.Account;
import com.banking.app.bankingApp.response.users.User;
import com.banking.app.bankingApp.service.balance.BalanceManagementService;
import com.banking.app.bankingApp.service.users.UserManagementService;

import java.util.ArrayList;
import java.util.List;

public class AccountManagementService {
    private static final AccountManagementService accountManagementService = new AccountManagementService();
    private AccountsDatabaseService accountsDatabaseService;
    private BalanceManagementService balanceManagementService;
    private UserManagementService userManagementService;
    private TokenUtil tokenUtil;

    private AccountManagementService() {
        accountsDatabaseService = AccountsDatabaseService.getInstance();
        balanceManagementService = BalanceManagementService.getInstance();
        userManagementService = UserManagementService.getInstance();
        tokenUtil = TokenUtil.getInstance();
    }

    public static AccountManagementService getInstance() {
        return accountManagementService;
    }

    public Account addAccount(CreateAccount createAccount, String userId) {
        User user = userManagementService.getUserById(userId);
        Account account = new Account();
        DBAccount dbAccount = accountsDatabaseService.createAccount(createAccount, userId);
        account.setId(dbAccount.getId());
        account.setUserId(userId);
        account.setOwner(dbAccount.getOwner());
        account.setExpirationDate(dbAccount.getExpirationDate());
        account.setAccountName(dbAccount.getAccountName());
        account.setCreatedAt(dbAccount.getCreatedAt());
        account.setBalance(0.0);
        if (account.getUserId().equals(userId)) {
            return account;
        } else {
            throw new IllegalArgumentException();
        }
    }


    public void updateAccount(String id, UpdateAccount updateAccount, String userId) {
        userManagementService.getUserById(userId);
        accountsDatabaseService.updateAccount(id, updateAccount);
    }

    public Account getAccountById(String id, String userId) {
        Account account = new Account();
        DBAccount dbAccount = accountsDatabaseService.findAccountById(id);
        account.setId(dbAccount.getId());
        account.setUserId(userId);
        account.setOwner(dbAccount.getOwner());
        account.setExpirationDate(dbAccount.getExpirationDate());
        account.setAccountName(dbAccount.getAccountName());
        account.setCreatedAt(dbAccount.getCreatedAt());
        account.setBalance(balanceManagementService.getBalance(account.getId()));
        if (account.getUserId().equals(userId)) {
            return account;
        } else {
            throw new IllegalArgumentException();
        }
    }


    public List<Account> getAllAccounts(String userId) {
        List<Account> accounts = new ArrayList<>();
        List<DBAccount> dbAccounts = accountsDatabaseService.getAllAccounts(userId);
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

    public void deleteAccountById(String id, String userId) {
        getAccountById(id, userId);
        accountsDatabaseService.deleteAccount(id);
    }
}
