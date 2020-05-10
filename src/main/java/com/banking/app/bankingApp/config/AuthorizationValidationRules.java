package com.banking.app.bankingApp.config;

import com.banking.app.bankingApp.database.roles.DBRoles;
import com.banking.app.bankingApp.database.users.DBUser;
import com.banking.app.bankingApp.database.users.UsersDatabaseService;

import java.util.ArrayList;
import java.util.List;

public class AuthorizationValidationRules {
    private static final AuthorizationValidationRules authorizationValidationRules = new AuthorizationValidationRules();
    private UsersDatabaseService usersDatabaseService;

    private AuthorizationValidationRules() {
        usersDatabaseService = UsersDatabaseService.getInstance();
    }

    public static AuthorizationValidationRules getInstance() {
        return authorizationValidationRules;
    }

    public boolean checkUserRole(String id, String role) {
        DBUser dbUser = usersDatabaseService.findUserById(id);
        List<DBRoles> dbRolesList = new ArrayList<>();
        dbRolesList.addAll(dbUser.getDbRoles());
        for (int i = 0; i < dbRolesList.size(); i++) {
            if (dbRolesList.get(i).getRoleName().equalsIgnoreCase("root")
                    || dbRolesList.get(i).getRoleName().equalsIgnoreCase(role)) {
                return true;
            } else {
                throw new RuntimeException();
            }
        }
        return false;
    }
}
