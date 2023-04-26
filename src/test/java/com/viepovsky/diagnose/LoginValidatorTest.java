package com.viepovsky.diagnose;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class LoginValidatorTest {
    private static final LoginValidator VALIDATOR = new LoginValidator();
    private static final SecurityContext SECURITY_CONTEXT = Mockito.mock(SecurityContext.class);
    private static final Authentication AUTHENTICATION = Mockito.mock(Authentication.class);

    @Test
    void should_return_true_if_user_login_matches_with_given_login() {
        //Given
        SecurityContextHolder.setContext(SECURITY_CONTEXT);
        when(SECURITY_CONTEXT.getAuthentication()).thenReturn(AUTHENTICATION);
        when(AUTHENTICATION.getName()).thenReturn("testLogin");
        //When
        boolean isAuthorized = VALIDATOR.isUserAuthorized("testLogin");
        //Then
        assertTrue(isAuthorized);
    }

    @Test
    void should_return_false_if_user_login_doesnt_match_with_given_login() {
        //Given
        SecurityContextHolder.setContext(SECURITY_CONTEXT);
        when(SECURITY_CONTEXT.getAuthentication()).thenReturn(AUTHENTICATION);
        when(AUTHENTICATION.getName()).thenReturn("testLogin22");
        //When
        boolean isAuthorized = VALIDATOR.isUserAuthorized("testLogin");
        //Then
        assertFalse(isAuthorized);
    }

}