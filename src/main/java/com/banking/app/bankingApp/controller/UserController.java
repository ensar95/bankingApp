package com.banking.app.bankingApp.controller;

import com.banking.app.bankingApp.config.AuthorizationValidationRules;
import com.banking.app.bankingApp.config.TokenUtil;
import com.banking.app.bankingApp.exceptions.UserRoleNotAllowed;
import com.banking.app.bankingApp.request.users.CreateUser;
import com.banking.app.bankingApp.request.users.UpdateUser;
import com.banking.app.bankingApp.response.users.User;
import com.banking.app.bankingApp.service.users.UserManagementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class UserController {
    private UserManagementService userManagementService;
    private TokenUtil tokenUtil;
    private AuthorizationValidationRules authorizationValidationRules;
    public UserController() {
        userManagementService = UserManagementService.getInstance();
        tokenUtil=TokenUtil.getInstance();
    }

    @PostMapping(value = "/users")

    public ResponseEntity<User> addUser(@RequestBody CreateUser createUser,
                                        @RequestHeader(name="Authorization") String authorization) {

        try {
            authorizationValidationRules.checkUserRole(tokenUtil.getIdFromToken(authorization),"createUser");
            User createdUser = userManagementService.addUser(createUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @GetMapping(value = "/users/{id}")

    public ResponseEntity<User> getUserById(@PathVariable("id") String id,
                                            @RequestHeader(name="Authorization") String authorization) {
        try {
            authorizationValidationRules.checkUserRole(tokenUtil.getIdFromToken(authorization),"getUser");
            User foundUser = userManagementService.getUserById(id);
            return ResponseEntity.status(HttpStatus.OK).body(foundUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping(value = "/users")

    public ResponseEntity<List<User>> getAllUsers(@RequestHeader(name="Authorization")String authorization) {
        try{
            authorizationValidationRules.checkUserRole(tokenUtil.getIdFromToken(authorization),"getUser");
            return ResponseEntity.status(HttpStatus.OK).body(userManagementService.getAllUsers());
    }catch (UserRoleNotAllowed e){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping(value = "/users/{id}")
    public ResponseEntity updateUser(@RequestBody UpdateUser updateUser,
                                     @PathVariable("id") String id,
                                     @RequestHeader(name="Authorization") String authorization) {
        try {
            authorizationValidationRules.checkUserRole(tokenUtil.getIdFromToken(authorization),"updateUser");
            userManagementService.getUserById(id);
            userManagementService.updateUser(id, updateUser);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping(value = "/users/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") String id,
                                     @RequestHeader(name="Authorization") String authorization) {
        try {
            authorizationValidationRules.checkUserRole(tokenUtil.getIdFromToken(authorization),"deleteUser");
            userManagementService.deleteUser(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
