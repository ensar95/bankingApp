package com.banking.app.bankingApp.service.users;

import com.banking.app.bankingApp.database.email.DBEmailVerificationCodeDatabaseService;
import com.banking.app.bankingApp.database.users.DBUser;
import com.banking.app.bankingApp.database.users.UsersDatabaseService;
import com.banking.app.bankingApp.request.users.CreateUser;
import com.banking.app.bankingApp.request.users.UpdateUser;
import com.banking.app.bankingApp.response.users.User;
import com.banking.app.bankingApp.service.email.EmailManagementService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserManagementService {
    @Autowired
    private UsersDatabaseService usersDatabaseService;
    @Autowired
    private EmailManagementService emailManagementService;
    @Autowired
    private DBEmailVerificationCodeDatabaseService dbEmailVerificationCodeDatabaseService;

    private UserManagementService() {
    }

    public User addUser(CreateUser createUser) {
        DBUser dbUser = usersDatabaseService.createDbUser(createUser);
        String code = emailManagementService.generateCode();
        User user = new User();
        user.setId(dbUser.getId());
        user.setFirstName(dbUser.getFirstName());
        user.setLastName(dbUser.getLastName());
        user.setEmail(dbUser.getEmail());
        user.setDateOfBirth(dbUser.getDateOfBirth());
        user.setOccupation(dbUser.getOccupation());
        user.setCurrentAddress(dbUser.getCurrentAddress());
        user.setPhoneNumber(dbUser.getPhoneNumber());
        user.setCreatedAt(dbUser.getCreatedAt());
        dbEmailVerificationCodeDatabaseService.createVerificationCode(user.getId(),code);
        try {
            emailManagementService.sendVerificationEmail(createUser.getEmail(), code);
        } catch (IOException e) {
            return null;
        } catch (MessagingException e) {
            return null;
        }
        return user;

    }

    public String encryptPassword(String password, String salt) {
        return BCrypt.hashpw(password, salt);
    }


    public User getUserById(String id) {
        DBUser dbUser = usersDatabaseService.findUserById(id);
        User user = new User();
        user.setId(dbUser.getId());
        user.setFirstName(dbUser.getFirstName());
        user.setLastName(dbUser.getLastName());
        user.setEmail(dbUser.getEmail());
        user.setDateOfBirth(dbUser.getDateOfBirth());
        user.setOccupation(dbUser.getOccupation());
        user.setCurrentAddress(dbUser.getCurrentAddress());
        user.setPhoneNumber(dbUser.getPhoneNumber());
        user.setCreatedAt(dbUser.getCreatedAt());
        return user;
    }

    public void updateUser(String id, UpdateUser updateUser) {
        usersDatabaseService.updateUser(id, updateUser);
    }

    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();
        List<DBUser> allDbUsers = usersDatabaseService.getAllUsers();
        for (int i = 0; i < allDbUsers.size(); i++) {
            User user = new User();
            user.setId(allDbUsers.get(i).getId());
            user.setFirstName(allDbUsers.get(i).getFirstName());
            user.setLastName(allDbUsers.get(i).getLastName());
            user.setEmail(allDbUsers.get(i).getEmail());
            user.setDateOfBirth(allDbUsers.get(i).getDateOfBirth());
            user.setOccupation(allDbUsers.get(i).getOccupation());
            user.setCurrentAddress(allDbUsers.get(i).getCurrentAddress());
            user.setPhoneNumber(allDbUsers.get(i).getPhoneNumber());
            user.setCreatedAt(allDbUsers.get(i).getCreatedAt());
            allUsers.add(user);
        }
        return allUsers;
    }

    public void deleteUser(String id) {
        DBUser dbUser = usersDatabaseService.findUserById(id);
        usersDatabaseService.deleteUser(id);
    }


    public User getUserByEmailAndPassword(String email, String password) {
        DBUser dbUser = usersDatabaseService.findUserByEmail(email);
        String hashPassword = BCrypt.hashpw(password, dbUser.getSalt());
        System.out.println(hashPassword);
        System.out.println(dbUser.getEncryptedPassword());
        BCrypt.checkpw(hashPassword, dbUser.getEncryptedPassword());
        User user = new User();
        user.setId(dbUser.getId());
        user.setFirstName(dbUser.getFirstName());
        user.setLastName(dbUser.getLastName());
        user.setEmail(dbUser.getEmail());
        user.setDateOfBirth(dbUser.getDateOfBirth());
        user.setOccupation(dbUser.getOccupation());
        user.setCurrentAddress(dbUser.getCurrentAddress());
        user.setPhoneNumber(dbUser.getPhoneNumber());
        user.setCreatedAt(dbUser.getCreatedAt());
        return user;

    }

}
