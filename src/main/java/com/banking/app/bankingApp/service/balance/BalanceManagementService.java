package com.banking.app.bankingApp.service.balance;

import com.banking.app.bankingApp.database.accounts.AccountsDatabaseService;
import com.banking.app.bankingApp.database.accounts.DBAccount;
import com.banking.app.bankingApp.database.transactions.DBTransaction;
import com.banking.app.bankingApp.database.transactions.TransactionsDatabaseService;

import java.util.List;

public class BalanceManagementService {
    private AccountsDatabaseService accountsDatabaseService;
    private TransactionsDatabaseService transactionsDatabaseService;

    public BalanceManagementService() {
        transactionsDatabaseService = new TransactionsDatabaseService();
        accountsDatabaseService = new AccountsDatabaseService();
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
        DBAccount dbAccount = accountsDatabaseService.findAccountById(id);
        dbAccount.setBalance(getIncome(id) - getExpenses(id));
        return dbAccount.getBalance();

    }
}
