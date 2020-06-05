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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionsDatabaseService {
    @Autowired
    private AccountsDatabaseService accountsDatabaseService;
    private SessionFactory sessionFactory;

    private TransactionsDatabaseService() {
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
        dbTransaction.setSourceAccountId(sourceAccount);
        DBAccount destinationAccount = accountsDatabaseService.findAccountById(createTransaction.getDestinationId());
        dbTransaction.setDestinationAccountId(destinationAccount);
        java.time.LocalDateTime now = java.time.LocalDateTime.now();
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

    public List<DBTransaction> getAllDBTransactionsWhereSource(String id) {
        Session session = sessionFactory.openSession();
        Query<DBTransaction> query = session.createQuery("from DBTransaction t where t.sourceAccountId.id=:id", DBTransaction.class);
        query.setParameter("id", id);
        List<DBTransaction> dbTransactions = query.getResultList();
        session.close();
        return dbTransactions;
    }

    public List<DBTransaction> getAllDBTransactionsWhereDestination(String id) {
        Session session = sessionFactory.openSession();
        Query<DBTransaction> query = session.createQuery("from DBTransaction t where t.destinationAccountId.id=:id", DBTransaction.class);
        query.setParameter("id", id);
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
        dbTransaction.setSourceAccountId(accountsDatabaseService.findAccountById(updateTransaction.getSourceId()));
        dbTransaction.setDestinationAccountId(accountsDatabaseService.findAccountById(updateTransaction.getDestinationId()));
        session.update(dbTransaction);
        transaction.commit();
        session.close();
    }

    public void deleteDBTransaction(String id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        DBTransaction dbTransaction = getDBTransactionById(id);
        session.delete(dbTransaction);
        transaction.commit();
        session.close();
    }

    public List<DBTransaction> getAllTransactionsByDateForId(String id, LocalDateTime startDate, LocalDateTime endDate) {
        Session session = sessionFactory.openSession();
        Query<DBTransaction> query = session.createQuery("from DBTransaction t where (t.destinationAccountId.id =:accountId or t.sourceAccountId.id =:accountId) and t.createdAt>= :start and t.createdAt< :end", DBTransaction.class);
        query.setParameter("accountId", id);
        query.setParameter("start", startDate);
        query.setParameter("end", endDate);
        List<DBTransaction> dbTransactions = query.getResultList();
        session.close();
        return dbTransactions;
    }
}
