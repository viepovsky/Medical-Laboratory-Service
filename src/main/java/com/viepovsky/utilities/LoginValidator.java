package com.viepovsky.utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class LoginValidator {
    private static final Logger logger = LoggerFactory.getLogger(LoginValidator.class);

    public boolean isUserAuthorized(String login) {
        logger.info("Validating if token belongs to given login: " + login);
        String loginFromToken = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean isAuthorized = loginFromToken.equals(login);
        if (isAuthorized) {
            logger.info("Token belongs to given login.");
        } else {
            logger.warn("Token doesn't belong to given login");
        }
        return isAuthorized;
    }
}
