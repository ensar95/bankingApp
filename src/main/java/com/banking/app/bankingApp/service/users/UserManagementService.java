package com.banking.app.bankingApp.service.users;

import com.banking.app.bankingApp.database.users.DBUser;
import com.banking.app.bankingApp.database.users.UsersDatabaseService;
import com.banking.app.bankingApp.request.users.CreateUser;
import com.banking.app.bankingApp.request.users.UpdateUser;
import com.banking.app.bankingApp.response.users.User;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


public class UserManagementService {
    private static final UserManagementService userManagementService = new UserManagementService();
    private UsersDatabaseService usersDatabaseService;

    private UserManagementService() {
        usersDatabaseService = UsersDatabaseService.getInstance();
    }

    public static UserManagementService getInstance() {
        return userManagementService;
    }


    public User addUser(CreateUser createUser) {
        DBUser dbUser = usersDatabaseService.createDbUser(createUser);
        User user = new User();
        user.setId(dbUser.getId());
        user.setFirstName(dbUser.getFirstName());
        user.setLastName(dbUser.getLastName());
        user.setEmail(dbUser.getEmail());
        user.setDateOfBirth(dbUser.getDateOfBirth());
        user.setOccupation(dbUser.getOccupation());
        user.setCurrentAdress(dbUser.getCurrentAddress());
        user.setPhoneNumber(dbUser.getPhoneNumber());
        user.setCreatedAt(dbUser.getCreatedAt());
        return user;
    }

    public String encryptPassword(CreateUser createUser) {
        String encryptedPassword = Base64.getEncoder().encodeToString(createUser.getPassword().getBytes());
        return encryptedPassword;
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
        user.setCurrentAdress(dbUser.getCurrentAddress());
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
            user.setCurrentAdress(allDbUsers.get(i).getCurrentAddress());
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
        String encodedPassword = Base64.getEncoder().encodeToString(password.getBytes());
        DBUser dbUser = usersDatabaseService.findUserByEmailAndEncryptedPassword(email, encodedPassword);
        User user = new User();
        user.setId(dbUser.getId());
        user.setFirstName(dbUser.getFirstName());
        user.setLastName(dbUser.getLastName());
        user.setEmail(dbUser.getEmail());
        user.setDateOfBirth(dbUser.getDateOfBirth());
        user.setOccupation(dbUser.getOccupation());
        user.setCurrentAdress(dbUser.getCurrentAddress());
        user.setPhoneNumber(dbUser.getPhoneNumber());
        user.setCreatedAt(dbUser.getCreatedAt());
        return user;
    }
}
