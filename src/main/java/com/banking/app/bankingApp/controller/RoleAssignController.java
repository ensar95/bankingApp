package com.banking.app.bankingApp.controller;

import com.banking.app.bankingApp.request.roleUserAssign.AssignRole;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoleAssignController {

    @PostMapping(value = "/users/roles")
    public ResponseEntity assignRoleToUser(AssignRole assignRole){
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
