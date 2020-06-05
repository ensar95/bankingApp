package com.banking.app.bankingApp.controller;

import com.banking.app.bankingApp.request.roles.CreateRole;
import com.banking.app.bankingApp.request.roles.UpdateRole;
import com.banking.app.bankingApp.response.roles.Roles;
import com.banking.app.bankingApp.service.roles.RolesManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RoleController {
    @Autowired
    private RolesManagementService rolesManagementService;

    public RoleController() {

    }

    @PostMapping(value = "/roles")
    public ResponseEntity<Roles> addRole(@RequestBody CreateRole createRole) {
        Roles roles = rolesManagementService.addRole(createRole);
        return ResponseEntity.status(HttpStatus.CREATED).body(roles);
    }

    @GetMapping(value = "/roles")
    public ResponseEntity<List<Roles>> getAllRoles() {

        return ResponseEntity.status(HttpStatus.OK).body(rolesManagementService.getAllRoles());
    }

    @GetMapping(value = "/roles/{id}")
    public ResponseEntity<Roles> getRoleById(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(rolesManagementService.getRoleById(id));
    }

    @PutMapping(value = "/roles/{id}")
    public ResponseEntity updateRoleById(@PathVariable String id,
                                         @RequestBody UpdateRole updateRole) {
        rolesManagementService.updateRoleById(id, updateRole);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping(value = "/roles/{id}")
    public ResponseEntity deleteRoleById(@PathVariable String id) {
        rolesManagementService.deleteRoleById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
