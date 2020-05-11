package com.banking.app.bankingApp.request.roleUserAssign;

public class AssignRole {
    private String userId;
    private String roleId;

    public AssignRole() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }


}
