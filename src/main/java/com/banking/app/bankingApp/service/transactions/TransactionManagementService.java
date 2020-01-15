package com.banking.app.bankingApp.service.transactions;

import com.banking.app.bankingApp.database.transactions.DBTransaction;
import com.banking.app.bankingApp.database.transactions.TransactionsDatabaseService;
import com.banking.app.bankingApp.request.transactions.CreateTransaction;
import com.banking.app.bankingApp.request.transactions.UpdateTransaction;
import com.banking.app.bankingApp.response.accounts.Account;
import com.banking.app.bankingApp.response.transactions.Transaction;
import com.banking.app.bankingApp.service.accounts.AccountManagementService;
import com.banking.app.bankingApp.service.balance.BalanceManagementService;

import java.util.ArrayList;
import java.util.List;

public class TransactionManagementService {
    private static final TransactionManagementService transactionManagementService = new TransactionManagementService();
    private TransactionsDatabaseService transactionsDatabaseService;
    private BalanceManagementService balanceManagementService;
    private AccountManagementService accountManagementService;

    public TransactionManagementService() {
        accountManagementService = AccountManagementService.getInstance();
        transactionsDatabaseService = TransactionsDatabaseService.getInstance();
        balanceManagementService = BalanceManagementService.getInstance();
    }

    public static TransactionManagementService getInstance() {
        return transactionManagementService;
    }

    private void verifyTransaction(String id) {
        Account account = accountManagementService.getAccountById(id);
        if (account.getBalance() - balanceManagementService.getBalance(id) < 0) {
            throw new IllegalStateException();
        } else if (account.getBalance() + balanceManagementService.getBalance(id) < 0) {
            throw new IllegalStateException();
        }
    }

    public Transaction addTransaction(CreateTransaction createTransaction) {
        Account account = accountManagementService.getAccountById(createTransaction.getSourceId());
        verifyTransaction(account.getId());
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

    public Transaction getTransactionById(String id) {
        Transaction transaction = new Transaction();
        DBTransaction dbTransaction = transactionsDatabaseService.getDBTransactionById(id);
        transaction.setId(dbTransaction.getId());
        transaction.setAmount(dbTransaction.getAmount());
        transaction.setPurpose(dbTransaction.getPurpose());
        transaction.setSourceId(dbTransaction.getSourceAccountId().getId());
        transaction.setDestinationId(dbTransaction.getDestinationAccountId().getId());
        transaction.setCreatedAt(dbTransaction.getCreatedAt());
        return transaction;
    }

    public List<Transaction> getAllTransactions(String id) {
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
            transactions.add(transaction);
        }
        return transactions;
    }

    public void updateTransaction(String id, UpdateTransaction updateTransaction) {
        Account account = accountManagementService.getAccountById(updateTransaction.getSourceId());
        verifyTransaction(updateTransaction.getSourceId());
        transactionsDatabaseService.updateDBTransaction(updateTransaction, id);
    }

    public void deleteTransaction(String id) {
        transactionsDatabaseService.deleteDBTransaction(id);
    }
}
