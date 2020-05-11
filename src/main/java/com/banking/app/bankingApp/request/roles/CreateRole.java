package com.banking.app.bankingApp.request.roles;

public class CreateRole {
    private String roleName;
    private String roleDescription;

    public String getRoleName() {
        return roleName;
    }

    public CreateRole() {
    }

    public CreateRole(String roleName, String roleDescription) {
        this.roleName = roleName;
        this.roleDescription = roleDescription;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

}
