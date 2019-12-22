package com.banking.app.bankingApp.database.users;

import com.banking.app.bankingApp.Users.CreateUser;
import com.banking.app.bankingApp.Users.DBUser;
import com.banking.app.bankingApp.Users.UpdateUser;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;


import java.io.File;
import java.util.List;

public class UsersDatabaseService {
    private SessionFactory sessionFactory;
    public UsersDatabaseService(){
        File f=new File("C:\\Users\\Ensar\\Desktop\\bankingApp\\src\\main\\resources\\hibernate.cfg.xml");
        sessionFactory = new Configuration().configure(f).buildSessionFactory();
    }
    public void createDbUser(CreateUser createUser){
        Session session=sessionFactory.openSession();
        Transaction transaction=session.beginTransaction();

        DBUser dbUser=new DBUser();
        dbUser.setId(createUser.getId());
        dbUser.setFirstName(createUser.getFirstName());
        dbUser.setLastName(createUser.getLastName());
        dbUser.setEmail(createUser.getEmail());
        dbUser.setDateOfBirth(createUser.getDateOfBirth());
        dbUser.setOccupation(createUser.getOccupation());
        dbUser.setCurrentAdress(createUser.getCurrentAdress());
        dbUser.setPhoneNumber(createUser.getPhoneNumber());
        dbUser.setCreatedAt(createUser.getCreatedAt());

        session.save(dbUser);
        transaction.commit();
        session.close();
    }
    public List<DBUser> getAllUsers(){
    Session session=sessionFactory.openSession();
        Query<DBUser> query = session.createQuery("from DBUser", DBUser.class);
        return query.getResultList();
    }
    public DBUser findUserById(String id){
    Session session = sessionFactory.openSession();
    Query<DBUser> query=session.createQuery("select u from DBUser u where u.id=:userId", DBUser.class);
    query.setParameter("userId", id);
    return query.getSingleResult();
    }
    public void updateUser(String id, UpdateUser updateUser){
    Session session=sessionFactory.openSession();
    Transaction transaction=session.beginTransaction();

    DBUser dbUser = findUserById(id);
    dbUser.setFirstName(updateUser.getFirstName());
    dbUser.setLastName(updateUser.getLastName());
    dbUser.setEmail(updateUser.getEmail());
    dbUser.setDateOfBirth(updateUser.getDateOfBirth());
    dbUser.setOccupation(updateUser.getOccupation());
    dbUser.setCurrentAdress(updateUser.getCurrentAdress());
    dbUser.setPhoneNumber(updateUser.getPhoneNumber());

    session.update(dbUser);
    transaction.commit();
    session.close();
    }
    public void deleteUser(String id){
    Session session=sessionFactory.openSession();
    Transaction transaction=session.beginTransaction();

    DBUser dbUser=new DBUser();
    dbUser.setId(id);

    session.delete(dbUser);
    transaction.commit();
    session.close();
    }
}
