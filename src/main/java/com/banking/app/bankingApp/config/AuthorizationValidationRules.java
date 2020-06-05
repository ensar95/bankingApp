package com.banking.app.bankingApp.config;

import com.banking.app.bankingApp.database.roles.DBRoles;
import com.banking.app.bankingApp.database.users.DBUser;
import com.banking.app.bankingApp.database.users.UsersDatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class AuthorizationValidationRules {
    @Autowired
    private UsersDatabaseService usersDatabaseService;

    private AuthorizationValidationRules() {
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
