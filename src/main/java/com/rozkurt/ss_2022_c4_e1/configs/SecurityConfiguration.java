package com.rozkurt.ss_2022_c4_e1.configs;

import com.rozkurt.ss_2022_c4_e1.security.filters.ApiKeyFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class SecurityConfiguration {

    @Value("${the.secret.key}")
    private String key;


    @Bean
    public SecurityFilterChain securityContextHolder(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity.httpBasic()
                .and()
                .addFilterBefore(new ApiKeyFilter(key), BasicAuthenticationFilter.class)
                .authorizeHttpRequests()
                .anyRequest()
                .authenticated()
                .and()
//              .and().authenticationManager() // ır by adding a bean of type AuthenticationManager
//              .and().authenticationProvider() it doesn't override the ApplicationProvider, it adds one more to the collection.

                .build();

    }

}
