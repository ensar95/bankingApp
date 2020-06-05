package com.banking.app.bankingApp.database.email;

import com.banking.app.bankingApp.database.users.DBUser;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "DBEmailVerificationCode")
public class DBEmailVerificationCode {
    @Id
    private String id;
    private String code;
    private LocalDateTime createdAt;
    private String flag;
    @ManyToOne
    @JoinColumn(name = "dbUser")
    private DBUser dbUser;

    public DBEmailVerificationCode() {
    }

    public DBUser getDbUser() {
        return dbUser;
    }

    public void setDbUser(DBUser dbUser) {
        this.dbUser = dbUser;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }


}
