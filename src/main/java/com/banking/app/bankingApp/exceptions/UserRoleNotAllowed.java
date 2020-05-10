package com.banking.app.bankingApp.exceptions;

public class UserRoleNotAllowed extends  RuntimeException{
    public UserRoleNotAllowed(String message){
        super(message);
    }

}
