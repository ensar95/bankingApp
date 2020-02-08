package com.banking.app.bankingApp.config;

import com.banking.app.bankingApp.request.users.UserLogin;
import com.banking.app.bankingApp.response.jwtToken.JWTToken;
import com.banking.app.bankingApp.response.users.User;
import com.banking.app.bankingApp.service.users.UserManagementService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class TokenUtil {
    private static final TokenUtil tokenUtil = new TokenUtil();
    private UserManagementService userManagementService;
    private Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    private TokenUtil() {
        userManagementService = UserManagementService.getInstance();
    }

    public static TokenUtil getInstance() {
        return tokenUtil;
    }

    public JWTToken getToken(UserLogin userLogin) {
        User user = userManagementService.getUserByEmailAndPassword(userLogin);
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
