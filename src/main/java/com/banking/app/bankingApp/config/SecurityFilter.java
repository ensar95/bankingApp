package com.banking.app.bankingApp.config;

import com.banking.app.bankingApp.service.users.UserManagementService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(1)
public class SecurityFilter implements Filter {
    TokenUtil tokenUtil;
    UserManagementService userManagementService;

    public SecurityFilter() {
        tokenUtil = TokenUtil.getInstance();
        userManagementService = UserManagementService.getInstance();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String authHeader = httpRequest.getHeader("Authorization");
        String currentURI = httpRequest.getRequestURI();
        String userURI = "/users";
        String tokenURI = "/token";
        if (currentURI.startsWith(userURI) || currentURI.startsWith(tokenURI)) {
            chain.doFilter(request, response);
        } else {
            try {
                if (authHeader != null & tokenUtil.validateToken(authHeader) & tokenUtil.validateTokenId(authHeader)) {
                    chain.doFilter(request, response);

                } else {
                    httpResponse.setStatus(401);
                }
            } catch (ExpiredJwtException e) {
                httpResponse.setStatus(401);
            } catch (JwtException e) {
                httpResponse.setStatus(401);
            }

        }
    }
}