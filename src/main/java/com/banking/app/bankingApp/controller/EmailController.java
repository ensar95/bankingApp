package com.banking.app.bankingApp.controller;

import com.banking.app.bankingApp.service.email.EmailManagementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import java.io.IOException;

@RestController
public class EmailController {
    private EmailManagementService emailManagementService = EmailManagementService.getInstance();
    @PostMapping(value = "/email")
    public ResponseEntity sendEmail() {
        try {
            emailManagementService.sendEmail();
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch (AddressException e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
        }catch (MessagingException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }catch (IOException e){
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).build();
        }
    }
}
