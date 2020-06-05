package com.banking.app.bankingApp.config;

import com.banking.app.bankingApp.request.users.UserLogin;
import com.banking.app.bankingApp.response.jwtToken.JWTToken;
import com.banking.app.bankingApp.response.users.User;
import com.banking.app.bankingApp.service.users.UserManagementService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class TokenUtil {
    private static TokenUtil tokenUtil;
    @Autowired
    private UserManagementService userManagementService;
    private Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    private TokenUtil() {
    }


    public JWTToken getToken(UserLogin userLogin) {
        User user = userManagementService.getUserByEmailAndPassword(userLogin.getEmail(), userLogin.getPassword());
        String token = Jwts
                .builder()
                .setSubject(user.getId())
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .signWith(key)
                .compact();
        JWTToken jwtToken = new JWTToken();
        jwtToken.setAccessToken(token);
        return jwtToken;
    }

    public String getIdFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

    }


    public Boolean validateToken(String token) {
        User user = userManagementService.getUserById(getIdFromToken(token));
        if (user == null) {
            return false;
        } else {
            return true;
        }
    }
}
