package com.banking.app.bankingApp.database.transactions;

import com.banking.app.bankingApp.database.accounts.AccountsDatabaseService;
import com.banking.app.bankingApp.database.accounts.DBAccount;
import com.banking.app.bankingApp.request.transactions.CreateTransaction;
import com.banking.app.bankingApp.request.transactions.UpdateTransaction;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class TransactionsDatabaseService {
    private AccountsDatabaseService accountsDatabaseService;
    private SessionFactory sessionFactory;

    public TransactionsDatabaseService() {
        accountsDatabaseService = new AccountsDatabaseService();
        File f = new File("C:\\Users\\Ensar\\Desktop\\bankingApp\\src\\main\\resources\\hibernate.cfg.xml");
        sessionFactory = new Configuration().configure(f).buildSessionFactory();
    }

    public DBTransaction addDBTransaction(CreateTransaction createTransaction) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        DBTransaction dbTransaction = new DBTransaction();
        String transactionId = UUID.randomUUID().toString();
        dbTransaction.setId(transactionId);
        dbTransaction.setAmount(createTransaction.getAmount());
        dbTransaction.setPurpose(createTransaction.getPurpose());
        DBAccount sourceAccount = accountsDatabaseService.findAccountById(createTransaction.getSourceId());
        dbTransaction.setSource(sourceAccount);
        DBAccount destinationAccount = accountsDatabaseService.findAccountById(createTransaction.getDestinationId());
        dbTransaction.setDestination(destinationAccount);
        LocalDateTime now = LocalDateTime.now();
        dbTransaction.setCreatedAt(now);
        session.save(dbTransaction);
        transaction.commit();
        session.close();
        return dbTransaction;
    }

    public DBTransaction getDBTransactionById(String id) {
        Session session = sessionFactory.openSession();
        Query<DBTransaction> query = session.createQuery("select a from DBTransaction a where a.id=:tranId", DBTransaction.class);
        query.setParameter("tranId", id);
        DBTransaction foundTransaction = query.getSingleResult();
        session.close();
        return foundTransaction;
    }

    public List<DBTransaction> getAllDBTransactions() {
        Session session = sessionFactory.openSession();
        Query<DBTransaction> query = session.createQuery("from DBTransaction", DBTransaction.class);
        List<DBTransaction> dbTransactions = query.getResultList();
        session.close();
        return dbTransactions;
    }

    public void updateDBTransaction(UpdateTransaction updateTransaction, String id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        DBTransaction dbTransaction = getDBTransactionById(id);
        dbTransaction.setAmount(updateTransaction.getAmount());
        dbTransaction.setPurpose(updateTransaction.getPurpose());
        dbTransaction.setSource(accountsDatabaseService.findAccountById(updateTransaction.getSourceId()));
        dbTransaction.setDestination(accountsDatabaseService.findAccountById(updateTransaction.getDestinationId()));
        session.update(dbTransaction);
        transaction.commit();
        session.close();
    }
    public void deleteDBTransaction(String id){
        Session session=sessionFactory.openSession();
        Transaction transaction=session.beginTransaction();
        DBTransaction dbTransaction=getDBTransactionById(id);
        session.delete(dbTransaction);
        transaction.commit();
        session.close();
    }
}
