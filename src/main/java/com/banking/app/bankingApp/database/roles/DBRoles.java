package com.banking.app.bankingApp.database.roles;

import com.banking.app.bankingApp.database.users.DBUser;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "DBROLES")
public class DBRoles {
    @Id
    private String id;
    private String roleName;
    private String roleDescription;
    private LocalDateTime createdAt;
    @ManyToMany(mappedBy = "dbRoles")
    private List<DBUser> dbUser = new ArrayList<>();



    public DBRoles() {
    }



    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
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
