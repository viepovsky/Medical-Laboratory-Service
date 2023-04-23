package com.viepovsky.user;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class UserServiceTest {
    @InjectMocks
    private UserService service;

    @Mock
    private UserRepository repository;

    @Test
    void should_get_and_return_user() {
        //Given
        var expectedUser = User.builder().login("testLogin").password("testPassword").role(Role.USER).build();
        when(repository.findUserByLogin(anyString())).thenReturn(Optional.of(expectedUser));
        //When
        var retrievedUser = service.getUserByLogin("test");
        //Then
        assertThat(retrievedUser).usingRecursiveComparison().isEqualTo(expectedUser);
        verify(repository, times(1)).findUserByLogin(anyString());
        verifyNoMoreInteractions(repository);
    }

    @Test
    void should_not_get_user_that_doesnt_exists() {
        //Given
        when(repository.findUserByLogin(anyString())).thenReturn(Optional.empty());
        //When & then
        assertThrows(EntityNotFoundException.class, () -> service.getUserByLogin("test"));
        verify(repository, times(1)).findUserByLogin(anyString());
        verifyNoMoreInteractions(repository);
    }

    @Test
    void should_create_user() {
        //Given
        var user = User.builder().login("testLogin").password("testPassword").role(Role.USER).build();
        when(repository.existsByLogin(anyString())).thenReturn(false);
        when(repository.save(any(User.class))).thenReturn(user);
        //When
        var expectedUser = service.createUser(user);
        //Then
        assertThat(user).usingRecursiveComparison().isEqualTo(expectedUser);
        verify(repository, times(1)).save(any(User.class));
        verifyNoMoreInteractions(repository);
    }

    @Test
    void should_not_create_user_if_already_exists() {
        //Given
        var user = User.builder().login("testLogin").password("testPassword").role(Role.USER).build();
        when(repository.existsByLogin(anyString())).thenReturn(true);
        //When & then
        assertThrows(EntityExistsException.class, () -> service.createUser(user));
        verify(repository, times(0)).save(any(User.class));
    }

    @Test
    void should_update_user() {
        //Given
        var user = User.builder().id(5L).login("testLogin").password("testPassword").role(Role.USER).build();
        when(repository.findUserByLogin(anyString())).thenReturn(Optional.of(user));
        when(repository.save(any(User.class))).thenReturn(user);
        //When
        service.updateUser(user);
        //Then
        verify(repository, times(1)).save(any(User.class));
        assertDoesNotThrow(() -> new EntityNotFoundException());
    }

    @Test
    void should_not_update_user_that_doesnt_exists() {
        //Given
        var user = User.builder().id(5L).login("testLogin").password("testPassword").role(Role.USER).build();
        when(repository.findUserByLogin(anyString())).thenReturn(Optional.empty());
        //When & then
        assertThrows(EntityNotFoundException.class, () -> service.updateUser(user));
        verify(repository, times(0)).save(any(User.class));
    }
}