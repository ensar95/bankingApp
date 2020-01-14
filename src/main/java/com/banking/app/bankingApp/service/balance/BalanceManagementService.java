package com.banking.app.bankingApp.service.balance;

import com.banking.app.bankingApp.database.accounts.AccountsDatabaseService;
import com.banking.app.bankingApp.database.accounts.DBAccount;
import com.banking.app.bankingApp.database.transactions.DBTransaction;
import com.banking.app.bankingApp.database.transactions.TransactionsDatabaseService;
import com.banking.app.bankingApp.response.accounts.Account;
import com.banking.app.bankingApp.service.accounts.AccountManagementService;

import java.util.List;

public class BalanceManagementService {
    private static final BalanceManagementService balanceManagementService = new BalanceManagementService();
    private AccountManagementService accountManagementService;
    private AccountsDatabaseService accountsDatabaseService;
    private TransactionsDatabaseService transactionsDatabaseService;

    public BalanceManagementService() {
        transactionsDatabaseService =  TransactionsDatabaseService.getInstance();
        accountsDatabaseService = AccountsDatabaseService.getInstance();
        accountManagementService = AccountManagementService.getInstance();
    }

    public static BalanceManagementService getInstance() {
        return balanceManagementService;
    }

    public Double getIncome(String id) {
        DBAccount dbAccount = accountsDatabaseService.findAccountById(id);
        List<DBTransaction> dbTransactions = transactionsDatabaseService.getAllDBTransactionsWhereSource(id);
        Double income = 0.0;
        for (int i = 0; i < dbTransactions.size(); i++) {
            income = income + dbTransactions.get(i).getAmount();
        }
        return income;
    }

    public Double getExpenses(String id) {
        DBAccount dbAccount = accountsDatabaseService.findAccountById(id);
        List<DBTransaction> dbTransactions = transactionsDatabaseService.getAllDBTransactionsWhereDestination(id);
        Double expense = 0.0;
        for (int i = 0; i < dbTransactions.size(); i++) {
            expense = expense + dbTransactions.get(i).getAmount();
        }
        return expense;
    }

    public Double getBalance(String id) {
        Account account = accountManagementService.getAccountById(id);
        account.setBalance(getIncome(id) - getExpenses(id));
        return account.getBalance();

    }
}
