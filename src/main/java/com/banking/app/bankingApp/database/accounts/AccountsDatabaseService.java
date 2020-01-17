package com.banking.app.bankingApp.database.accounts;

import com.banking.app.bankingApp.database.users.UsersDatabaseService;
import com.banking.app.bankingApp.request.accounts.CreateAccount;
import com.banking.app.bankingApp.request.accounts.UpdateAccount;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class AccountsDatabaseService {
    private static final AccountsDatabaseService accountsDatabaseService = new AccountsDatabaseService();
    private UsersDatabaseService usersDatabaseService;
    private SessionFactory sessionFactory;
    public AccountsDatabaseService() {
        usersDatabaseService = UsersDatabaseService.getInstance();
        File f = new File("C:\\Users\\Ensar\\Desktop\\bankingApp\\src\\main\\resources\\hibernate.cfg.xml");
        sessionFactory = new Configuration().configure(f).buildSessionFactory();
    }

    public static AccountsDatabaseService getInstance() {
        return accountsDatabaseService;
    }

    public DBAccount createAccount(CreateAccount createAccount) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        DBAccount dbAccount = new DBAccount();
        String accountId = UUID.randomUUID().toString();
        dbAccount.setId(accountId);
        dbAccount.setDbUser(usersDatabaseService.findUserById(createAccount.getUserId()));
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

    public List<DBAccount> getAllAccounts() {
        Session session = sessionFactory.openSession();

        Query<DBAccount> query = session.createQuery("from DBAccount", DBAccount.class);
        List<DBAccount> allAccounts = query.getResultList();

        session.close();
        return allAccounts;
    }

    public void updateAccount(String id, UpdateAccount updateAccount) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        DBAccount dbAccount = findAccountById(id);
        dbAccount.setAccountName(updateAccount.getAccountName());
        dbAccount.setExpirationDate(updateAccount.getExpirationDate());
        dbAccount.setDbUser(usersDatabaseService.findUserById(updateAccount.getUserId()));

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
