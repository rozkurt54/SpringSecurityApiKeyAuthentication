package com.rozkurt.ss_2022_c4_e1.security.managers;

import com.rozkurt.ss_2022_c4_e1.security.providers.CustomApiKeyAuthenticationProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;


public class CustomAuthenticationManager implements AuthenticationManager {

    private final String key;

    public CustomAuthenticationManager(String key) {
        this.key = key;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        var provider = new CustomApiKeyAuthenticationProvider(key);

        if (provider.supports(authentication.getClass())){
            return provider.authenticate(authentication);
        }

        return authentication;
    }
}
