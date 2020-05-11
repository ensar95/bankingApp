package com.banking.app.bankingApp.service.roles;

import com.banking.app.bankingApp.database.roles.DBRoles;
import com.banking.app.bankingApp.database.roles.RolesDatabaseService;
import com.banking.app.bankingApp.database.users.DBUser;
import com.banking.app.bankingApp.database.users.UsersDatabaseService;
import com.banking.app.bankingApp.request.roleUserAssign.AssignRole;
import com.banking.app.bankingApp.request.roles.CreateRole;
import com.banking.app.bankingApp.request.roles.UpdateRole;
import com.banking.app.bankingApp.response.roles.Roles;
import com.banking.app.bankingApp.response.users.User;

import java.util.ArrayList;
import java.util.List;

public class RolesManagementService {
    private static final RolesManagementService rolesManagementService = new RolesManagementService();
    private RolesDatabaseService rolesDatabaseService;
    private UsersDatabaseService usersDatabaseService;

    private RolesManagementService() {
        rolesDatabaseService = RolesDatabaseService.getInstance();
        usersDatabaseService= UsersDatabaseService.getInstance();
    }

    public static RolesManagementService getInstance() {
        return rolesManagementService;
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
        for (int i=0; i< dbRolesList.size(); i++){
            Roles roles = new Roles();
            roles.setId(dbRolesList.get(i).getId());
            roles.setRoleName(dbRolesList.get(i).getRoleName());
            roles.setRoleDescription(dbRolesList.get(i).getRoleDescription());
            roles.setCreatedAt(dbRolesList.get(i).getCreatedAt());
            rolesList.add(roles);
        }
        return rolesList;
    }
    public Roles getRoleById(String id){
        Roles roles = new Roles();
        DBRoles dbRoles = rolesDatabaseService.getRoleById(id);
        roles.setId(dbRoles.getId());
        roles.setRoleName(dbRoles.getRoleName());
        roles.setRoleDescription(dbRoles.getRoleDescription());
        roles.setCreatedAt(dbRoles.getCreatedAt());
        return roles;
    }
    public void updateRoleById(String id, UpdateRole updateRole){
        rolesDatabaseService.updateRoleById(id, updateRole);
    }
    public void deleteRoleById(String id){
        rolesDatabaseService.deleteRoleById(id);
    }
    private boolean validateUserRoles(List<DBRoles> dbRolesList, DBRoles dbRoles) {
        boolean isValid = false;
        for (int i = 0; i < dbRolesList.size(); i++) {
            if (dbRolesList.get(i).equals(dbRoles)) {
                return false;
            }
        }
        return true;
    }

    private boolean validateAllRoles(DBRoles dbRoles) {
        boolean isValid = false;
        List<DBRoles> dbRolesList = rolesDatabaseService.getAllRoles();
        for (int i = 0; i < dbRolesList.size(); i++) {
            if (dbRolesList.get(i).equals(dbRoles)) {
                return false;
            }
        }
        return true;
    }

    public void assignRoleToUser(AssignRole assignRole) {
        DBUser dbUser = usersDatabaseService.findUserById(assignRole.getUserId());
        DBRoles dbRoles = rolesDatabaseService.getRoleById(assignRole.getRoleId());
        if (validateAllRoles(dbRoles)) {
            List<DBRoles> userRoles = dbUser.getDbRoles();
            if (validateUserRoles(userRoles, dbRoles)) {
                usersDatabaseService.addRoleToUser(dbRoles, assignRole.getUserId());
                rolesDatabaseService.addUserToRole(dbUser, assignRole.getRoleId());
            } else {
                throw new RuntimeException();
            }
        } else {
            throw new RuntimeException();
        }

    }
}
