package com.banking.app.bankingApp.config;

import com.banking.app.bankingApp.database.users.DBUser;
import com.banking.app.bankingApp.database.users.UsersDatabaseService;
import com.banking.app.bankingApp.request.users.UserLogin;
import com.banking.app.bankingApp.response.jwtToken.JWTToken;
import com.banking.app.bankingApp.response.users.User;
import com.banking.app.bankingApp.service.users.UserManagementService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.joda.time.DateTime;

import java.security.Key;
import java.util.Date;

public class TokenUtil {
    private static final TokenUtil tokenUtil = new TokenUtil();
    UserManagementService userManagementService;
    UsersDatabaseService usersDatabaseService;
    private Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    private TokenUtil() {
        userManagementService = UserManagementService.getInstance();
        usersDatabaseService=UsersDatabaseService.getInstance();
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
        Boolean validation = false;
        Date date = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody().getExpiration();
        DateTime now = DateTime.now();
        Date currentTime = now.toDate();
        if (currentTime.before(date)) {
            validation = true;
        } else {
            validation = false;
        }
        return validation;
    }

    public Boolean validateTokenId(String token) {
        DBUser dbUser=usersDatabaseService.findUserById(getIdFromToken(token));
        if(dbUser==null){
            return false;
        }
        else{
            return true;
        }
    }
}
