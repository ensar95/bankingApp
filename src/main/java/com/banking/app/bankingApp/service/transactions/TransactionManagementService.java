package com.banking.app.bankingApp.service.transactions;

import com.banking.app.bankingApp.database.transactions.DBTransaction;
import com.banking.app.bankingApp.database.transactions.TransactionsDatabaseService;
import com.banking.app.bankingApp.request.transactions.CreateTransaction;
import com.banking.app.bankingApp.request.transactions.UpdateTransaction;
import com.banking.app.bankingApp.response.accounts.Account;
import com.banking.app.bankingApp.response.transactions.Transaction;
import com.banking.app.bankingApp.service.accounts.AccountManagementService;
import com.banking.app.bankingApp.service.balance.BalanceManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionManagementService {
    @Autowired
    private TransactionsDatabaseService transactionsDatabaseService;
    @Autowired
    private BalanceManagementService balanceManagementService;
    @Autowired
    private AccountManagementService accountManagementService;

    private TransactionManagementService() {
    }


    private void verifyTransaction(String sourceAccountId, Double amount, String userId) {
        Account sourceAccount = accountManagementService.getAccountById(sourceAccountId, userId);
        if (sourceAccount.getBalance() < amount) {
            throw new IllegalStateException();
        }
    }

    private Account validateAccount(String userId, String accountId) {
        List<Account> accounts = accountManagementService.getAllAccounts(userId);
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getId().equals(accountId)) {
                return accounts.get(i);
            }
        }
        throw new IllegalArgumentException();
    }

    public Transaction addTransaction(CreateTransaction createTransaction, String userId) {
        validateAccount(userId, createTransaction.getSourceId());
        verifyTransaction(createTransaction.getSourceId(), createTransaction.getAmount(), userId);
        DBTransaction dbTransaction = transactionsDatabaseService.addDBTransaction(createTransaction);
        Transaction transaction = new Transaction();
        transaction.setId(dbTransaction.getId());
        transaction.setAmount(dbTransaction.getAmount());
        transaction.setSourceId(dbTransaction.getSourceAccountId().getId());
        transaction.setDestinationId(dbTransaction.getDestinationAccountId().getId());
        transaction.setPurpose(dbTransaction.getPurpose());
        transaction.setCreatedAt(dbTransaction.getCreatedAt());
        return transaction;

    }

    public Transaction getTransactionById(String id, String userId) {
        Transaction transaction = new Transaction();
        DBTransaction dbTransaction = transactionsDatabaseService.getDBTransactionById(id);
        transaction.setId(dbTransaction.getId());
        transaction.setAmount(dbTransaction.getAmount());
        transaction.setPurpose(dbTransaction.getPurpose());
        transaction.setSourceId(dbTransaction.getSourceAccountId().getId());
        transaction.setDestinationId(dbTransaction.getDestinationAccountId().getId());
        transaction.setCreatedAt(dbTransaction.getCreatedAt());

        validateAccount(userId, transaction.getSourceId());

        return transaction;
    }

    public List<Transaction> getAllTransactions(String id, String userId) {
        List<Transaction> transactions = new ArrayList<>();
        List<DBTransaction> dbTransactions = transactionsDatabaseService.getAllDBTransactionsWhereSource(id);
        List<DBTransaction> dbTransactions1 = transactionsDatabaseService.getAllDBTransactionsWhereDestination(id);
        for (int i = 0; i < dbTransactions.size(); i++) {
            Transaction transaction = new Transaction();
            transaction.setId(dbTransactions.get(i).getId());
            transaction.setAmount(dbTransactions.get(i).getAmount());
            transaction.setPurpose(dbTransactions.get(i).getPurpose());
            transaction.setSourceId(dbTransactions.get(i).getSourceAccountId().getId());
            transaction.setDestinationId(dbTransactions.get(i).getDestinationAccountId().getId());
            transaction.setCreatedAt(dbTransactions.get(i).getCreatedAt());
            validateAccount(userId, transaction.getSourceId());
            transactions.add(transaction);
        }
        for (int i = 0; i < dbTransactions1.size(); i++) {
            Transaction transaction = new Transaction();
            transaction.setId(dbTransactions1.get(i).getId());
            transaction.setAmount(dbTransactions1.get(i).getAmount());
            transaction.setPurpose(dbTransactions1.get(i).getPurpose());
            transaction.setSourceId(dbTransactions1.get(i).getSourceAccountId().getId());
            transaction.setDestinationId(dbTransactions1.get(i).getDestinationAccountId().getId());
            transaction.setCreatedAt(dbTransactions1.get(i).getCreatedAt());
            validateAccount(userId, transaction.getDestinationId());
            transactions.add(transaction);
        }
        return transactions;
    }

    public void updateTransaction(String id, UpdateTransaction updateTransaction, String userId) {
        verifyTransaction(updateTransaction.getSourceId(), updateTransaction.getAmount(), userId);
        transactionsDatabaseService.updateDBTransaction(updateTransaction, id);
    }

    public void deleteTransaction(String id, String userId) {
        Transaction transaction = getTransactionById(id, userId);
        validateAccount(userId, transaction.getSourceId());
        transactionsDatabaseService.deleteDBTransaction(id);
    }
}
