package com.banking.app.bankingApp.service.root;

import com.banking.app.bankingApp.database.roles.DBRoles;
import com.banking.app.bankingApp.database.roles.RolesDatabaseService;
import com.banking.app.bankingApp.database.users.DBUser;
import com.banking.app.bankingApp.database.users.UsersDatabaseService;
import com.banking.app.bankingApp.request.roleUserAssign.AssignRole;
import com.banking.app.bankingApp.request.roles.CreateRole;
import com.banking.app.bankingApp.request.users.CreateUser;
import com.banking.app.bankingApp.service.roleAssign.RoleAssignManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class RootUserService {
    @Autowired
    RoleAssignManagementService roleAssignManagementService;
    @Autowired
    RolesDatabaseService rolesDatabaseService;
    @Autowired
    UsersDatabaseService usersDatabaseService;


    public void createRootUser() {
        List<DBUser> userList = usersDatabaseService.getAllUsers();
        if (userList.size() == 0) {
            try {
                String date = new String("19/02/1995");
                Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(date);
                CreateUser createUser = new CreateUser("Ensar",
                        "Skopljak",
                        "ensarskopljak@gmail.com",
                        date1,
                        "Student",
                        "Hadzeli 179",
                        "061/356-735",
                        "pass123");
                DBUser user = usersDatabaseService.createDbUser(createUser);
                CreateRole createRole = new CreateRole("Root",
                        "This role allows us to operate on everything through the app," +
                                " without having to remove some validation rules");
                DBRoles roles = rolesDatabaseService.createDbRole(createRole);
                AssignRole assignRole = new AssignRole();
                assignRole.setUserId(user.getId());
                assignRole.setRoleId(roles.getId());
                roleAssignManagementService.assignRoleToUser(assignRole);
            } catch (ParseException e) {
            }
        }
    }
}

