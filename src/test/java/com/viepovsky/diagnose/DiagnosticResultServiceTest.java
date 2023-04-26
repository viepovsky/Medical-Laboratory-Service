package com.viepovsky.diagnose;

import com.viepovsky.user.User;
import com.viepovsky.user.UserService;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class DiagnosticResultServiceTest {
    @InjectMocks
    private DiagnosticResultService diagnosticResultService;

    @Mock
    private UserService userService;

    @Mock
    private DiagnosticResultRepository repository;

    @Test
    void should_get_all_DiagnosticResults_for_given_login() {
        //Given
        var result = DiagnosticResult.builder().build();
        List<DiagnosticResult> results = new ArrayList<>(List.of(result));
        when(repository.getDiagnosticResultByUser_Login(anyString())).thenReturn(results);
        //When
        List<DiagnosticResult> retrievedResults = diagnosticResultService.getAllDiagnosticResults("test");
        //Then
        assertThat(retrievedResults).isNotNull();
        assertEquals(1, retrievedResults.size());
    }

    @Test
    void should_save_DiagnosticResult() {
        //Given
        var result = DiagnosticResult.builder().type(DiagnosticType.BLOOD).build();
        var user = new User();
        when(userService.getUserByLogin(anyString())).thenReturn(user);
        doNothing().when(userService).updateUser(any(User.class));
        when(repository.save(any(DiagnosticResult.class))).thenReturn(result);
        //When
        var retrievedResult = diagnosticResultService.saveDiagnosticResult(result, "test");
        //Then
        assertThat(retrievedResult).isNotNull();
        assertEquals(result.getType(), retrievedResult.getType());
    }
}