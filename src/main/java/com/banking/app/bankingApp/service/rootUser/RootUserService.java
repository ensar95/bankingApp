package com.banking.app.bankingApp.service.rootUser;

import com.banking.app.bankingApp.database.users.DBUser;
import com.banking.app.bankingApp.database.users.UsersDatabaseService;
import com.banking.app.bankingApp.request.roleUserAssign.AssignRole;
import com.banking.app.bankingApp.request.roles.CreateRole;
import com.banking.app.bankingApp.request.users.CreateUser;
import com.banking.app.bankingApp.response.roles.Roles;
import com.banking.app.bankingApp.service.roles.RolesManagementService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RootUserService {
    private UsersDatabaseService usersDatabaseService;
    private RolesManagementService rolesManagementService;
    private static final RootUserService rootUserService = new RootUserService();

    public static RootUserService getInstance() {
        return rootUserService;
    }

    private RootUserService() {
        usersDatabaseService = UsersDatabaseService.getInstance();
        rolesManagementService = RolesManagementService.getInstance();
    }

    public void checkRootUser() {
        List<DBUser> userList = usersDatabaseService.getAllUsersByRole("root");
        if (userList.size() == 0) {

            {

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
                    Roles roles = rolesManagementService.addRole(createRole);
                    AssignRole assignRole = new AssignRole();
                    assignRole.setUserId(user.getId());
                    assignRole.setRoleId(roles.getId());
                    rolesManagementService.assignRoleToUser(assignRole);
                } catch (ParseException e) {
                }
            }
        }
    }
}