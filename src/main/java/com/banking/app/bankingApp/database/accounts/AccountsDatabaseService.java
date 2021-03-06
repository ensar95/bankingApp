package com.banking.app.bankingApp.database.accounts;

import com.banking.app.bankingApp.database.users.UsersDatabaseService;
import com.banking.app.bankingApp.request.accounts.CreateAccount;
import com.banking.app.bankingApp.request.accounts.UpdateAccount;
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
public class AccountsDatabaseService {
    @Autowired
    private UsersDatabaseService usersDatabaseService;
    private SessionFactory sessionFactory;

    private AccountsDatabaseService() {
        File f = new File("C:\\Users\\Ensar\\Desktop\\bankingApp\\src\\main\\resources\\hibernate.cfg.xml");
        sessionFactory = new Configuration().configure(f).buildSessionFactory();
    }

    public DBAccount createAccount(CreateAccount createAccount, String userId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        DBAccount dbAccount = new DBAccount();
        String accountId = UUID.randomUUID().toString();
        dbAccount.setId(accountId);
        dbAccount.setDbUser(usersDatabaseService.findUserById(userId));
        dbAccount.setExpirationDate(createAccount.getExpirationDate());
        LocalDateTime now = LocalDateTime.now();
        dbAccount.setCreatedAt(now);
        dbAccount.setAccountName(createAccount.getAccountName());

        session.save(dbAccount);
        transaction.commit();
        session.close();
        return dbAccount;
    }

    public DBAccount findAccountById(String id) {
        Session session = sessionFactory.openSession();
        Query<DBAccount> query = session.createQuery("select a from DBAccount a where a.id=:accId", DBAccount.class);
        query.setParameter("accId", id);
        DBAccount foundAcc = query.getSingleResult();
        session.close();
        return foundAcc;
    }

    public List<DBAccount> getAllAccounts(String userId) {
        Session session = sessionFactory.openSession();

        Query<DBAccount> query = session.createQuery("from DBAccount a where userId=:userId", DBAccount.class);
        query.setParameter("userId", userId);
        List<DBAccount> allAccounts = query.getResultList();

        session.close();
        return allAccounts;
    }

    public void updateAccount(String id, UpdateAccount updateAccount, String userId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        DBAccount dbAccount = findAccountById(id);
        dbAccount.setAccountName(updateAccount.getAccountName());
        dbAccount.setExpirationDate(updateAccount.getExpirationDate());
        dbAccount.setDbUser(usersDatabaseService.findUserById(userId));

        session.update(dbAccount);
        transaction.commit();
        session.close();
    }

    public void deleteAccount(String id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        DBAccount dbAccount = findAccountById(id);
        session.delete(dbAccount);
        transaction.commit();
        session.close();
    }
}
