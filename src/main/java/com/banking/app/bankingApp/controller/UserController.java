package com.banking.app.bankingApp.controller;

import com.banking.app.bankingApp.Users.CreateUser;
import com.banking.app.bankingApp.Users.UpdateUser;
import com.banking.app.bankingApp.service.users.UserManagementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    private UserManagementService userManagementService;
    public UserController(){
        userManagementService = new UserManagementService();
    }
    @PostMapping(value="/users")
    public ResponseEntity<CreateUser> addUser(@RequestBody CreateUser createUser){
        userManagementService.addUser(createUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(createUser);
    }
    @GetMapping(value="/users/{id}")
    public ResponseEntity<UpdateUser> getUserById(@PathVariable("id")String id){
    UpdateUser foundUser=userManagementService.getUserById(id);
    return ResponseEntity.status(HttpStatus.OK).body(foundUser);
    }
    @GetMapping(value="/users")
    public ResponseEntity<List<CreateUser>> getAllUsers(){
    return ResponseEntity.status(HttpStatus.OK).body(userManagementService.getAllUsers());
    }
    @PutMapping(value="/users/{id}")
    public ResponseEntity<UpdateUser> updateUser(@RequestBody UpdateUser updateUser, @PathVariable("id") String id){
    userManagementService.getUserById(id);
    userManagementService.updateUser(id, updateUser);
    return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @DeleteMapping(value="/users/{id}")
    public ResponseEntity deleteUser(@PathVariable ("id") String id){
        userManagementService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
