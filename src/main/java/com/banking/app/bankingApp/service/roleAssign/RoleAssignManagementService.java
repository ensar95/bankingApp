package com.banking.app.bankingApp.service.roleAssign;

import com.banking.app.bankingApp.database.roles.DBRoles;
import com.banking.app.bankingApp.database.roles.RolesDatabaseService;
import com.banking.app.bankingApp.database.users.DBUser;
import com.banking.app.bankingApp.database.users.UsersDatabaseService;
import com.banking.app.bankingApp.request.roleUserAssign.AssignRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleAssignManagementService {
    @Autowired
    private UsersDatabaseService usersDatabaseService;
    @Autowired
    private RolesDatabaseService rolesDatabaseService;

    private RoleAssignManagementService() {
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
