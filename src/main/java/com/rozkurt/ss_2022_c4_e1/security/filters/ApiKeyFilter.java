package com.rozkurt.ss_2022_c4_e1.security.filters;

import com.rozkurt.ss_2022_c4_e1.security.authentication.ApiKeyAuthentication;
import com.rozkurt.ss_2022_c4_e1.security.managers.CustomAuthenticationManager;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class ApiKeyFilter extends OncePerRequestFilter {

    private final String key;

    public ApiKeyFilter(String key) {
        this.key = key;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        var requestKey = request.getHeader("x-api-key");

        if(requestKey == null || "null".equals(requestKey)) {
            filterChain.doFilter(request,response);
        }

        CustomAuthenticationManager customAuthenticationManager = new CustomAuthenticationManager(key);

        var auth = new ApiKeyAuthentication(requestKey);

        try {
            var a = customAuthenticationManager.authenticate(auth);
            if(a.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(a);
                filterChain.doFilter(request, response);
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        } catch (AuthenticationException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
