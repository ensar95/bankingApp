package com.banking.app.bankingApp.controller;

import com.banking.app.bankingApp.request.email.CheckEmailVerificationCode;
import com.banking.app.bankingApp.service.email.EmailVerificationManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NoResultException;

@RestController
public class EmailController {
    @Autowired
    EmailVerificationManagementService emailVerificationManagementService;

    @PostMapping(value = "/email")
    public ResponseEntity verifyEmail(@RequestBody CheckEmailVerificationCode checkEmailVerificationCode) {
        try {
            emailVerificationManagementService.checkVerificationCode(checkEmailVerificationCode.getCode(),checkEmailVerificationCode.getUserId());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();}
        catch (NoResultException | IllegalStateException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
