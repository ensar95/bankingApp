package com.banking.app.bankingApp.config;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(1)
public class SecurityFilter implements Filter {
    @Autowired
    TokenUtil tokenUtil;

    public SecurityFilter() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String authHeader = httpRequest.getHeader("Authorization");
        String currentURI = httpRequest.getRequestURI();
        String userURI = "/users";
        String roleURI = "/roles";
        String tokenURI = "/token";
        String emailURI = "/email";
        if (currentURI.startsWith(userURI) || currentURI.startsWith(tokenURI) || currentURI.startsWith(emailURI) || currentURI.startsWith(roleURI)) {
            chain.doFilter(request, response);
        } else {
            try {
                if (authHeader != null && tokenUtil.validateToken(authHeader)) {
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