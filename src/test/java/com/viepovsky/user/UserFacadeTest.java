package com.viepovsky.user;

import com.viepovsky.exceptions.PasswordValidationException;
import com.viepovsky.user.dto.request.RegisterUserRequest;
import com.viepovsky.user.dto.request.UpdateUserRequest;
import com.viepovsky.user.dto.response.CreatedUserResponse;
import com.viepovsky.user.dto.response.DetailsUserResponse;
import io.github.viepovsky.polishutils.pesel.InvalidPeselException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class UserFacadeTest {
    @InjectMocks
    private UserFacade facade;

    @Mock
    private UserService service;

    @Mock
    private UserMapper mapper;

    @Test
    void should_get_user_by_login() {
        //Given
        var user = new User();
        var userResponse = new DetailsUserResponse();

        when(service.getUserByLogin(anyString())).thenReturn(user);
        when(mapper.mapToDetailsUserResponse(any(User.class))).thenReturn(userResponse);
        //When
        var retrievedUser = facade.getUserByLogin("login");
        //Then
        assertNotNull(retrievedUser);
    }

    @Test
    void should_create_user() {
        //Given
        var userRequest = new RegisterUserRequest();
        var user = new User();
        var userResponse = new CreatedUserResponse();

        when(mapper.mapToUser(any(RegisterUserRequest.class))).thenReturn(user);
        when(service.createUser(any(User.class))).thenReturn(user);
        when(mapper.mapToCreatedUserResponse(any(User.class))).thenReturn(userResponse);
        //When
        var retrievedUser = facade.createUser(userRequest);
        //Then
        assertNotNull(retrievedUser);
        verify(service, times(1)).createUser(any(User.class));
    }

    @Test
    void should_update_user_without_password() throws InvalidPeselException, PasswordValidationException {
        //Given
        var userRequest = new UpdateUserRequest();
        var user = new User();

        when(mapper.mapToUser(any(UpdateUserRequest.class))).thenReturn(user);
        doNothing().when(service).updateUser(any(User.class));
        //When
        facade.updateUser(userRequest);
        //Then
        verify(service, times(1)).updateUser(any(User.class));
    }

    @Test
    void should_update_user_with_password() throws InvalidPeselException, PasswordValidationException {
        //Given
        var userRequest = new UpdateUserRequest();
        var user = new User();
        user.setPassword("testpassword");

        when(mapper.mapToUser(any(UpdateUserRequest.class))).thenReturn(user);
        doNothing().when(service).updateUserWithPassword(any(User.class));
        //When
        facade.updateUser(userRequest);
        //Then
        verify(service, times(1)).updateUserWithPassword(any(User.class));
    }
}