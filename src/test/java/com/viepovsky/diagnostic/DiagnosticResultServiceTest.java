package com.viepovsky.diagnostic;

import com.viepovsky.user.User;
import com.viepovsky.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class DiagnosticResultServiceTest {
    @InjectMocks
    private DiagnosticResultService service;

    @Mock
    private UserRepository userRepository;

    @Mock
    private DiagnosticResultRepository resultRepository;

    @Test
    void should_get_all_DiagnosticResults_for_given_login() {
        //Given
        var result = DiagnosticResult.builder().build();
        List<DiagnosticResult> results = new ArrayList<>(List.of(result));
        when(resultRepository.getDiagnosticResultByUser_Login(anyString())).thenReturn(results);
        //When
        List<DiagnosticResult> retrievedResults = service.getAllDiagnosticResults("test");
        //Then
        assertThat(retrievedResults).isNotNull();
        assertEquals(1, retrievedResults.size());
    }

    @Test
    void should_save_DiagnosticResult() {
        //Given
        var result = DiagnosticResult.builder().type(DiagnosticType.BLOOD).build();
        var user = new User();
        when(userRepository.findUserByLogin(anyString())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(resultRepository.save(any(DiagnosticResult.class))).thenReturn(result);
        //When
        var retrievedResult = service.saveDiagnosticResult(result, "test");
        //Then
        assertThat(retrievedResult).isNotNull();
        assertEquals(result.getType(), retrievedResult.getType());
    }

    @Test
    void should_not_save_DiagnosticResult_if_given_user_doesnt_exists() {
        //Given
        var result = DiagnosticResult.builder().type(DiagnosticType.BLOOD).build();
        when(userRepository.findUserByLogin(anyString())).thenReturn(Optional.empty());
        //When & then
        assertThrows(EntityNotFoundException.class, () -> service.saveDiagnosticResult(result, "test"));
        verifyNoInteractions(resultRepository);
    }
}