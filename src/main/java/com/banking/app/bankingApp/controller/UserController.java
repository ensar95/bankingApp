package com.banking.app.bankingApp.controller;

import com.banking.app.bankingApp.request.users.CreateUser;
import com.banking.app.bankingApp.request.users.UpdateUser;
import com.banking.app.bankingApp.response.users.User;
import com.banking.app.bankingApp.service.users.UserManagementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.List;

@RestController

public class UserController {
    private UserManagementService userManagementService;

    public UserController() {
        userManagementService = new UserManagementService();
    }

    @PostMapping(value = "/users")

    public ResponseEntity<User> addUser(@RequestBody CreateUser createUser) {

        try {

            User createdUser = userManagementService.addUser(createUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (ConstraintViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

    @GetMapping(value = "/users/{id}")

    public ResponseEntity<User> getUserById(@PathVariable("id") String id) {
        try {
            User foundUser = userManagementService.getUserById(id);
            return ResponseEntity.status(HttpStatus.OK).body(foundUser);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping(value = "/users")

    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userManagementService.getAllUsers());
    }

    @PutMapping(value = "/users/{id}")
    public ResponseEntity updateUser(@RequestBody UpdateUser updateUser, @PathVariable("id") String id) {
        try {
            userManagementService.getUserById(id);
            userManagementService.updateUser(id, updateUser);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping(value = "/users/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") String id) {
        try {
            userManagementService.deleteUser(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
