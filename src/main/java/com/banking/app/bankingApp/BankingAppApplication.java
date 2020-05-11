package com.banking.app.bankingApp;

import com.banking.app.bankingApp.service.rootUser.RootUserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BankingAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankingAppApplication.class, args);
        RootUserService rootUserService = RootUserService.getInstance();
        rootUserService.checkRootUser();
    }
}
