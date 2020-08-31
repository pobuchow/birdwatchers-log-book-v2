/*
package com.blb.main.service;

import com.blb.main.dto.LoginCredentialsTO;
import com.blb.main.service.exception.UserAuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@Component
public class HeaderAuthenticationService extends OncePerRequestFilter {

    @Autowired
    private UserService userService;

    private static final List<String> URL_AUTH_EXCLUSION = Arrays.asList("/users/add", "/users/authenticate");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (URL_AUTH_EXCLUSION.contains(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }

        final String auth = request.getHeader("auth");
        String[] decodedAuth = new String(Base64.getDecoder().decode(auth)).split(":");

        try {
            LoginCredentialsTO credentialsTO = userService.authorize(decodedAuth[0], decodedAuth[1]);
            final UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(credentialsTO.getUsername(), credentialsTO.getPassword());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        } catch (UserAuthenticationException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "invalid token");
        }
    }
}*/
