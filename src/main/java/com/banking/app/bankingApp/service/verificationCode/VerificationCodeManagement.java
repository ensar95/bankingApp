package com.banking.app.bankingApp.service.verificationCode;

import com.banking.app.bankingApp.database.email.DBEmailVerificationCodeDatabaseService;
import com.banking.app.bankingApp.response.users.User;
import com.banking.app.bankingApp.service.email.EmailManagementService;
import com.banking.app.bankingApp.service.users.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import java.io.IOException;

@Service
public class VerificationCodeManagement {
    @Autowired
    EmailManagementService emailManagementService;
    @Autowired
    UserManagementService userManagementService;
    @Autowired
    DBEmailVerificationCodeDatabaseService dbEmailVerificationCodeDatabaseService;

    public void resendEmailVerificationCode(String userId) throws AddressException, MessagingException, IOException {
        User user = userManagementService.getUserById(userId);
        String code =emailManagementService.generateCode();
        dbEmailVerificationCodeDatabaseService.createVerificationCode(userId,code);
        emailManagementService.sendVerificationEmail(user.getEmail(), code);
    }
}
