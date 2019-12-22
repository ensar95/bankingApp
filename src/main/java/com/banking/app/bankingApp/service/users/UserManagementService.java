package com.banking.app.bankingApp.service.users;

import com.banking.app.bankingApp.Users.CreateUser;
import com.banking.app.bankingApp.Users.DBUser;
import com.banking.app.bankingApp.Users.UpdateUser;
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
    public UpdateUser getUserById(String id){
    DBUser dbUser=usersDatabaseService.findUserById(id);
    UpdateUser updateUser=new UpdateUser();
    updateUser.setId(dbUser.getId());
    updateUser.setFirstName(dbUser.getFirstName());
    updateUser.setLastName(dbUser.getLastName());
    updateUser.setEmail(dbUser.getEmail());
    updateUser.setDateOfBirth(dbUser.getDateOfBirth());
    updateUser.setOccupation(dbUser.getOccupation());
    updateUser.setCurrentAdress(dbUser.getCurrentAdress());
    updateUser.setPhoneNumber(dbUser.getPhoneNumber());
    return updateUser;
    }
    public void updateUser(String id, UpdateUser updateUser){
    usersDatabaseService.updateUser(id, updateUser);
    }

    public List<CreateUser> getAllUsers(){
    List<CreateUser> allUsers = new ArrayList<>();
    List<DBUser> allDbUsers=usersDatabaseService.getAllUsers();
    CreateUser createUser=new CreateUser();
    for (int i=0; i<allDbUsers.size(); i++){
        createUser.setId(allDbUsers.get(i).getId());
        createUser.setFirstName(allDbUsers.get(i).getFirstName());
        createUser.setLastName(allDbUsers.get(i).getLastName());
        createUser.setEmail(allDbUsers.get(i).getEmail());
        createUser.setDateOfBirth(allDbUsers.get(i).getDateOfBirth());
        createUser.setOccupation(allDbUsers.get(i).getOccupation());
        createUser.setCurrentAdress(allDbUsers.get(i).getCurrentAdress());
        createUser.setPhoneNumber(allDbUsers.get(i).getPhoneNumber());
        createUser.setCreatedAt(allDbUsers.get(i).getCreatedAt());
        allUsers.add(createUser);
    }
    return allUsers;
    }

    public void deleteUser(String id){
        DBUser dbUser=usersDatabaseService.findUserById(id);
        usersDatabaseService.deleteUser(id);
    }
}
