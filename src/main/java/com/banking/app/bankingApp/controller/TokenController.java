package com.banking.app.bankingApp.controller;

import com.banking.app.bankingApp.config.TokenUtil;
import com.banking.app.bankingApp.request.users.UserLogin;
import com.banking.app.bankingApp.response.jwtToken.JWTToken;
import com.banking.app.bankingApp.service.users.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {
    @Autowired
    UserManagementService userManagementService;
    @Autowired
    TokenUtil tokenUtil;

    public TokenController() {
    }

    @PostMapping(value = "/token")
    public ResponseEntity<JWTToken> getTokenForUser(@RequestBody UserLogin userLogin) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(tokenUtil.getToken(userLogin));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
