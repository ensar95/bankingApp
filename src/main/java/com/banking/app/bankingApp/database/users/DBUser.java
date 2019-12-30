package com.banking.app.bankingApp.database.users;

import com.banking.app.bankingApp.database.accounts.DBAccount;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
@Entity
@Table(name="DBUSER")
public class DBUser {
    @Id
    private String dbUser_id;
    @NotEmpty(message = "First name has to be entered")
    private String firstName;
    @NotEmpty(message = "Last name has to be entered")
    private String lastName;
    @NotEmpty(message = "Email has to be entered")
    private String email;
    @NotEmpty(message = "Date of birth has to entered")
    private Date dateOfBirth;
    @NotEmpty(message = "Occupation has to be entered")
    private String occupation;
    @NotEmpty(message = "Current adress has to be entered")
    private String currentAdress;
    @NotEmpty(message = "Phone number has to entered")
    private String phoneNumber;
    private LocalDateTime createdAt;
    @OneToMany(mappedBy ="dbUser" )
    private List<DBAccount> dbAccount;

    public DBUser() {
    }

    public List<DBAccount> getDbAccount() {
        return dbAccount;
    }

    public void setDbAccount(List<DBAccount> dbAccount) {
        this.dbAccount = dbAccount;
    }

    public String getDbUser_id() {
        return dbUser_id;
    }

    public void setDbUser_id(String dbUser_id) {
        this.dbUser_id = dbUser_id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getCurrentAdress() {
        return currentAdress;
    }

    public void setCurrentAdress(String currentAdress) {
        this.currentAdress = currentAdress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
