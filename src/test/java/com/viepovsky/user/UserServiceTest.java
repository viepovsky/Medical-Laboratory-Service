package com.viepovsky.user;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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
    void should_find_and_return_user_for_login_purposes() {
        //Given
        var expectedUser = Mockito.mock(User.class);
        when(repository.findUserByLogin(anyString())).thenReturn(Optional.of(expectedUser));
        //When
        var retrievedUser = service.findUserByLogin(anyString());
        //Then
        assertThat(retrievedUser).usingRecursiveComparison().isEqualTo(expectedUser);
        verify(repository, times(1)).findUserByLogin(anyString());
        verifyNoMoreInteractions(repository);
    }

    @Test
    void should_not_find_user_for_login_purposes_that_doesnt_exist() {
        //Given
        when(repository.findUserByLogin(anyString())).thenReturn(Optional.empty());
        //When & then
        assertThrows(EntityNotFoundException.class, () -> service.findUserByLogin(anyString()));
        verify(repository, times(1)).findUserByLogin(anyString());
        verifyNoMoreInteractions(repository);
    }
}