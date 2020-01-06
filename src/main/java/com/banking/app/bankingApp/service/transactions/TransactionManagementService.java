package com.banking.app.bankingApp.service.transactions;

import com.banking.app.bankingApp.database.transactions.DBTransaction;
import com.banking.app.bankingApp.database.transactions.TransactionsDatabaseService;
import com.banking.app.bankingApp.request.transactions.CreateTransaction;
import com.banking.app.bankingApp.request.transactions.UpdateTransaction;
import com.banking.app.bankingApp.response.transactions.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TransactionManagementService {
    private TransactionsDatabaseService transactionsDatabaseService;

    public TransactionManagementService() {
        transactionsDatabaseService = new TransactionsDatabaseService();
    }

    public Transaction addTransaction(CreateTransaction createTransaction) {
        Transaction transaction = new Transaction();
        DBTransaction dbTransaction = transactionsDatabaseService.addDBTransaction(createTransaction);
        transaction.setId(dbTransaction.getId());
        transaction.setAmount(dbTransaction.getAmount());
        transaction.setSourceId(dbTransaction.getSource().getId());
        transaction.setDestinationId(dbTransaction.getDestination().getId());
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
        transaction.setSourceId(dbTransaction.getSource().getId());
        transaction.setDestinationId(dbTransaction.getDestination().getId());
        transaction.setCreatedAt(dbTransaction.getCreatedAt());
        return transaction;
    }

    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        List<DBTransaction> dbTransactions = transactionsDatabaseService.getAllDBTransactions();
        for (int i = 0; i < dbTransactions.size(); i++) {
            Transaction transaction = new Transaction();
            transaction.setId(dbTransactions.get(i).getId());
            transaction.setAmount(dbTransactions.get(i).getAmount());
            transaction.setPurpose(dbTransactions.get(i).getPurpose());
            transaction.setSourceId(dbTransactions.get(i).getSource().getId());
            transaction.setDestinationId(dbTransactions.get(i).getDestination().getId());
            transaction.setCreatedAt(dbTransactions.get(i).getCreatedAt());
        }
        return transactions;
    }

    public void updateTransaction(String id, UpdateTransaction updateTransaction) {
        transactionsDatabaseService.updateDBTransaction(updateTransaction, id);
    }
    public void deleteTransaction(String id){
        transactionsDatabaseService.deleteDBTransaction(id);
    }
}
