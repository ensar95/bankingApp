package com.banking.app.bankingApp.service.balance;

import com.banking.app.bankingApp.database.accounts.AccountsDatabaseService;
import com.banking.app.bankingApp.database.accounts.DBAccount;
import com.banking.app.bankingApp.database.transactions.DBTransaction;
import com.banking.app.bankingApp.database.transactions.TransactionsDatabaseService;
import com.banking.app.bankingApp.service.accounts.AccountManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BalanceManagementService {
    @Autowired
    private AccountManagementService accountManagementService;
    @Autowired
    private AccountsDatabaseService accountsDatabaseService;
    @Autowired
    private TransactionsDatabaseService transactionsDatabaseService;

    private BalanceManagementService() {
    }


    private Double getIncome(String id) {
        DBAccount dbAccount = accountsDatabaseService.findAccountById(id);
        List<DBTransaction> dbTransactions = transactionsDatabaseService.getAllDBTransactionsWhereDestination(id);
        Double income = 0.0;
        for (int i = 0; i < dbTransactions.size(); i++) {
            income = income + dbTransactions.get(i).getAmount();
        }
        return income;
    }

    private Double getExpenses(String id) {
        DBAccount dbAccount = accountsDatabaseService.findAccountById(id);
        List<DBTransaction> dbTransactions = transactionsDatabaseService.getAllDBTransactionsWhereSource(id);
        Double expense = 0.0;
        for (int i = 0; i < dbTransactions.size(); i++) {
            expense = expense + dbTransactions.get(i).getAmount();
        }
        return expense;
    }

    public Double getBalance(String id) {
        return getIncome(id) - getExpenses(id);
    }
}
