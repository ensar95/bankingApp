package com.banking.app.bankingApp.database.email;

import com.banking.app.bankingApp.database.users.UsersDatabaseService;
import com.banking.app.bankingApp.service.users.UserManagementService;
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
public class DBEmailVerificationCodeDatabaseService {
    private SessionFactory sessionFactory;
    @Autowired
    private UsersDatabaseService usersDatabaseService;

    private DBEmailVerificationCodeDatabaseService() {
        File f = new File("C:\\Users\\Ensar\\Desktop\\bankingApp\\src\\main\\resources\\hibernate.cfg.xml");
        sessionFactory = new Configuration().configure(f).buildSessionFactory();
    }
    public List<DBEmailVerificationCode> getPreviousCodes(String userId){
        Session session = sessionFactory.openSession();

        Query<DBEmailVerificationCode> query = session.createQuery("from DBEmailVerificationCode v where v.dbUser.id=:userId",DBEmailVerificationCode.class);
        query.setParameter("userId", userId);
        List<DBEmailVerificationCode> dbEmailVerificationCodes = query.getResultList();

        session.close();
        return dbEmailVerificationCodes;
    }
    public DBEmailVerificationCode createVerificationCode(String userId, String code) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        DBEmailVerificationCode dbEmailVerificationCode = new DBEmailVerificationCode();
        String dbEmailVerificationCodeId = UUID.randomUUID().toString();
        dbEmailVerificationCode.setId(dbEmailVerificationCodeId);
        dbEmailVerificationCode.setCode(code);
        dbEmailVerificationCode.setDbUser(usersDatabaseService.findUserById(userId));
        dbEmailVerificationCode.setFlag("Pending");
        LocalDateTime now = LocalDateTime.now();
        dbEmailVerificationCode.setCreatedAt(now);

        session.save(dbEmailVerificationCode);
        transaction.commit();
        session.close();
        return dbEmailVerificationCode;
    }

    public DBEmailVerificationCode getVerificationCode(String userId, String code) {
        Session session = sessionFactory.openSession();

        Query<DBEmailVerificationCode> query = session.createQuery("from DBEmailVerificationCode v where v.code=:code and v.dbUser.id=:userId", DBEmailVerificationCode.class);
        query.setParameter("code", code);
        query.setParameter("userId", userId);
        DBEmailVerificationCode dbEmailVerificationCode = query.getSingleResult();
        session.close();
        return dbEmailVerificationCode;
    }

    public DBEmailVerificationCode getVerificationCodeById(String id) {
        Session session = sessionFactory.openSession();
        Query<DBEmailVerificationCode> query = session.createQuery("from DBEmailVerificationCode v where v.id=:id", DBEmailVerificationCode.class);
        query.setParameter("id", id);
        DBEmailVerificationCode dbEmailVerificationCode = query.getSingleResult();
        session.close();
        return dbEmailVerificationCode;
    }

    public void updateVerificationCodeFlag(String id, String flag) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        DBEmailVerificationCode dbEmailVerificationCode = getVerificationCodeById(id);
        dbEmailVerificationCode.setFlag(flag);

        session.update(dbEmailVerificationCode);
        transaction.commit();
        session.close();
    }
}
