package com.banking.app.bankingApp.service.roles;

import com.banking.app.bankingApp.database.roles.DBRoles;
import com.banking.app.bankingApp.database.roles.RolesDatabaseService;
import com.banking.app.bankingApp.request.roles.CreateRole;
import com.banking.app.bankingApp.request.roles.UpdateRole;
import com.banking.app.bankingApp.response.roles.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RolesManagementService {
    @Autowired
    private RolesDatabaseService rolesDatabaseService;

    private RolesManagementService() {
    }


    public Roles addRole(CreateRole createRole) {
        DBRoles dbRoles = rolesDatabaseService.createDbRole(createRole);
        Roles roles = new Roles();
        roles.setId(dbRoles.getId());
        roles.setRoleName(dbRoles.getRoleName());
        roles.setRoleDescription(dbRoles.getRoleDescription());
        roles.setCreatedAt(dbRoles.getCreatedAt());
        return roles;
    }

    public List<Roles> getAllRoles() {
        List<DBRoles> dbRolesList = rolesDatabaseService.getAllRoles();
        List<Roles> rolesList = new ArrayList();
        for (int i = 0; i < dbRolesList.size(); i++) {
            Roles roles = new Roles();
            roles.setId(dbRolesList.get(i).getId());
            roles.setRoleName(dbRolesList.get(i).getRoleName());
            roles.setRoleDescription(dbRolesList.get(i).getRoleDescription());
            roles.setCreatedAt(dbRolesList.get(i).getCreatedAt());
            rolesList.add(roles);
        }
        return rolesList;
    }

    public Roles getRoleById(String id) {
        Roles roles = new Roles();
        DBRoles dbRoles = rolesDatabaseService.getRoleById(id);
        roles.setId(dbRoles.getId());
        roles.setRoleName(dbRoles.getRoleName());
        roles.setRoleDescription(dbRoles.getRoleDescription());
        roles.setCreatedAt(dbRoles.getCreatedAt());
        return roles;
    }

    public void updateRoleById(String id, UpdateRole updateRole) {
        rolesDatabaseService.updateRoleById(id, updateRole);
    }

    public void deleteRoleById(String id) {
        rolesDatabaseService.deleteRoleById(id);
    }
}
