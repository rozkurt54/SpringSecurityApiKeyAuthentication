package com.rozkurt.ss_2022_c4_e1.security.providers;

import com.rozkurt.ss_2022_c4_e1.security.authentication.ApiKeyAuthentication;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;


public class CustomApiKeyAuthenticationProvider implements AuthenticationProvider {
    private final String key;


    public CustomApiKeyAuthenticationProvider(String key) {
        this.key = key;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        var auth = (ApiKeyAuthentication) authentication;

        if(this.key.equals(auth.getKey())){
            auth.setAuthenticated(true);
            return auth;
        }

        throw new BadCredentialsException("Bad credentials");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return ApiKeyAuthentication.class.equals(authentication);
    }
}
