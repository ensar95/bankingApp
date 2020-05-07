package com.banking.app.bankingApp.controller;

import com.banking.app.bankingApp.request.roleUserAssign.AssignRole;
import com.banking.app.bankingApp.service.roleAssign.RoleAssignManagementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoleAssignController {

    RoleAssignManagementService roleAssignManagementService;

    public RoleAssignController() {
        roleAssignManagementService = RoleAssignManagementService.getInstance();
    }

    @PostMapping(value = "/users/roles")
    public ResponseEntity assignRoleToUser(AssignRole assignRole) {
        try {
            roleAssignManagementService.assignRoleToUser(assignRole);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }
}
