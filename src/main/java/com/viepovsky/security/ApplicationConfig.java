package com.viepovsky.security;

import com.viepovsky.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
    private final UserService service;
    @Bean
    UserDetailsService userDetailsService() {
        return service::getUserByLogin;
    }
}
