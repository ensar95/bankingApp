package com.banking.app.bankingApp.database.users;

import com.banking.app.bankingApp.config.SecurityConstants;
import com.banking.app.bankingApp.request.users.CreateUser;
import com.banking.app.bankingApp.request.users.UpdateUser;
import com.banking.app.bankingApp.service.users.UserManagementService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.mindrot.jbcrypt.BCrypt;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/* UsersDatabaseService is singleton service */

public class UsersDatabaseService {
    private static  UsersDatabaseService userDatabaseService = new UsersDatabaseService();
    private UserManagementService userManagementService;
    private SessionFactory sessionFactory;

    private UsersDatabaseService() {
        File f = new File("C:\\Users\\Ensar\\Desktop\\bankingApp\\src\\main\\resources\\hibernate.cfg.xml");
        sessionFactory = new Configuration().configure(f).buildSessionFactory();
        userManagementService = UserManagementService.getInstance();
        System.out.println();
    }

    public static UsersDatabaseService getInstance() {
        return userDatabaseService;
    }

    public DBUser createDbUser(CreateUser createUser) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        DBUser dbUser = new DBUser();
        String userId = UUID.randomUUID().toString();
        dbUser.setId(userId);
        dbUser.setFirstName(createUser.getFirstName());
        dbUser.setLastName(createUser.getLastName());
        dbUser.setEmail(createUser.getEmail());
        dbUser.setDateOfBirth(createUser.getDateOfBirth());
        dbUser.setOccupation(createUser.getOccupation());
        dbUser.setCurrentAddress(createUser.getCurrentAddress());
        dbUser.setPhoneNumber(createUser.getPhoneNumber());
        LocalDateTime now = LocalDateTime.now();
        dbUser.setCreatedAt(now);
        String salt=BCrypt.gensalt();
        dbUser.setEncryptedPassword(userManagementService.encryptPassword(createUser.getPassword(), salt));
        dbUser.setSalt(salt);

        session.save(dbUser);
        transaction.commit();
        session.close();
        return dbUser;
    }

    public List<DBUser> getAllUsers() {
        Session session = sessionFactory.openSession();
        Query<DBUser> query = session.createQuery("from DBUser", DBUser.class);
        List<DBUser> allUsers = query.getResultList();
        session.close();
        return allUsers;
    }

    public DBUser findUserByEmail(String email) {
        Session session = sessionFactory.openSession();
        Query<DBUser> query = session.createQuery("select u from DBUser u where u.email=:userEmail", DBUser.class);
        query.setParameter("userEmail", email);
        DBUser foundUser = query.getSingleResult();
        session.close();
        return foundUser;
    }

    public DBUser findUserById(String id) {
        Session session = sessionFactory.openSession();
        Query<DBUser> query = session.createQuery("select u from DBUser u where u.id=:userId", DBUser.class);
        query.setParameter("userId", id);
        DBUser foundUser = query.getSingleResult();
        session.close();
        return foundUser;
    }

    public void updateUser(String id, UpdateUser updateUser) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        DBUser dbUser = findUserById(id);
        dbUser.setFirstName(updateUser.getFirstName());
        dbUser.setLastName(updateUser.getLastName());
        dbUser.setEmail(updateUser.getEmail());
        dbUser.setDateOfBirth(updateUser.getDateOfBirth());
        dbUser.setOccupation(updateUser.getOccupation());
        dbUser.setCurrentAddress(updateUser.getCurrentAddress());
        dbUser.setPhoneNumber(updateUser.getPhoneNumber());

        session.update(dbUser);
        transaction.commit();
        session.close();
    }

    public void deleteUser(String id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        DBUser dbUser = findUserById(id);

        session.delete(dbUser);
        transaction.commit();
        session.close();
    }
}
