package com.banking.app.bankingApp.service.users;

import com.banking.app.bankingApp.Users.CreateUser;
import com.banking.app.bankingApp.Users.DBUser;
import com.banking.app.bankingApp.Users.UpdateUser;
import com.banking.app.bankingApp.Users.User;
import com.banking.app.bankingApp.database.users.UsersDatabaseService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserManagementService {
    private UsersDatabaseService usersDatabaseService;
    public UserManagementService(){
        usersDatabaseService=new UsersDatabaseService();
    }
    public void addUser (CreateUser createUser){
        String randomString = UUID.randomUUID().toString();
        createUser.setId(randomString);
        LocalDateTime now=LocalDateTime.now();
        createUser.setCreatedAt(now);
        usersDatabaseService.createDbUser(createUser);
    }
    public User getUserById(String id){
    DBUser dbUser=usersDatabaseService.findUserById(id);
    User user=new User();
    user.setId(dbUser.getId());
    user.setFirstName(dbUser.getFirstName());
    user.setLastName(dbUser.getLastName());
    user.setEmail(dbUser.getEmail());
    user.setDateOfBirth(dbUser.getDateOfBirth());
    user.setOccupation(dbUser.getOccupation());
    user.setCurrentAdress(dbUser.getCurrentAdress());
    user.setPhoneNumber(dbUser.getPhoneNumber());
    return user;
    }
    public void updateUser(String id, UpdateUser updateUser){
    usersDatabaseService.updateUser(id, updateUser);
    }

    public List<User> getAllUsers(){
    List<User> allUsers = new ArrayList<>();
    List<DBUser> allDbUsers=usersDatabaseService.getAllUsers();
    for (int i=0; i<allDbUsers.size(); i++){
        User user=new User();
        user.setId(allDbUsers.get(i).getId());
        user.setFirstName(allDbUsers.get(i).getFirstName());
        user.setLastName(allDbUsers.get(i).getLastName());
        user.setEmail(allDbUsers.get(i).getEmail());
        user.setDateOfBirth(allDbUsers.get(i).getDateOfBirth());
        user.setOccupation(allDbUsers.get(i).getOccupation());
        user.setCurrentAdress(allDbUsers.get(i).getCurrentAdress());
        user.setPhoneNumber(allDbUsers.get(i).getPhoneNumber());
        allUsers.add(user);
    }
    return allUsers;
    }

    public void deleteUser(String id){
        DBUser dbUser=usersDatabaseService.findUserById(id);
        usersDatabaseService.deleteUser(id);
    }
}
