package com.banking.app.bankingApp.database.roles;

import com.banking.app.bankingApp.database.users.DBUser;
import com.banking.app.bankingApp.request.roles.CreateRole;
import com.banking.app.bankingApp.request.roles.UpdateRole;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
@Service
public class RolesDatabaseService {
    private SessionFactory sessionFactory;

    private RolesDatabaseService() {
        File f = new File("C:\\Users\\Ensar\\Desktop\\bankingApp\\src\\main\\resources\\hibernate.cfg.xml");
        sessionFactory = new Configuration().configure(f).buildSessionFactory();
    }


    public DBRoles createDbRole(CreateRole createRole) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        DBRoles dbRoles = new DBRoles();
        String roleId = UUID.randomUUID().toString();
        dbRoles.setId(roleId);
        dbRoles.setRoleName(createRole.getRoleName());
        dbRoles.setRoleDescription(createRole.getRoleDescription());
        LocalDateTime now = LocalDateTime.now();
        dbRoles.setCreatedAt(now);

        session.save(dbRoles);
        transaction.commit();
        session.close();
        return dbRoles;
    }
    public List<DBRoles> getAllRoles(){
        Session session = sessionFactory.openSession();
        Query<DBRoles> query=session.createQuery("from DBRoles", DBRoles.class);
        List<DBRoles> dbRoles=query.getResultList();
        session.close();
        return  dbRoles;
    }
    public DBRoles getRoleById(String id){
        Session session = sessionFactory.openSession();
        Query<DBRoles> query = session.createQuery("select a from DBRoles a where a.id := id", DBRoles.class);
        query.setParameter("id", id);
        DBRoles dbRoles=query.getSingleResult();
        session.close();
        return dbRoles;
    }
    public void updateRoleById(String id, UpdateRole updateRole){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        DBRoles dbRoles=getRoleById(id);
        dbRoles.setRoleName(updateRole.getRoleName());
        dbRoles.setRoleDescription(updateRole.getRoleDescription());

        session.update(dbRoles);
        transaction.commit();
        session.close();
    }
    public void deleteRoleById(String id){
        Session session=sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        DBRoles dbRoles= getRoleById(id);

        session.delete(dbRoles);
        transaction.commit();
        session.close();
    }
    public void addUserToRole(DBUser dbUser, String roleId){
        Session session=sessionFactory.openSession();
        Transaction transaction=session.beginTransaction();

        DBRoles dbRoles = getRoleById(roleId);
        List<DBUser> dbUsers=dbRoles.getDbUser();
        dbUsers.add(dbUser);

        session.save(dbRoles);
        transaction.commit();
        session.close();
    }
}
