package com.banking.app.bankingApp.controller;

import com.banking.app.bankingApp.config.TokenUtil;
import com.banking.app.bankingApp.request.email.CheckEmailVerificationCode;
import com.banking.app.bankingApp.service.email.EmailVerificationManagementService;
import com.banking.app.bankingApp.service.verificationCode.VerificationCodeManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.persistence.NoResultException;
import java.io.IOException;

@RestController
public class EmailController {
    @Autowired
    EmailVerificationManagementService emailVerificationManagementService;
    @Autowired
    VerificationCodeManagement verificationCodeManagement;
    @Autowired
    TokenUtil tokenUtil;

    @PostMapping(value = "/email")
    public ResponseEntity verifyEmail(@RequestBody CheckEmailVerificationCode checkEmailVerificationCode) {
        try {
            emailVerificationManagementService.checkVerificationCode(checkEmailVerificationCode.getCode(), checkEmailVerificationCode.getUserId());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (NoResultException | IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping(value = "/email/resend")
    public ResponseEntity resendEmail(@RequestHeader(name = "Authorization") String authorization) {
        try {
            verificationCodeManagement.resendEmailVerificationCode(tokenUtil.getIdFromToken(authorization));
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (IOException | MessagingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
