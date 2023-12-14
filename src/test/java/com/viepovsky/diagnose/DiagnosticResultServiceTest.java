package com.viepovsky.diagnose;

import com.viepovsky.user.User;
import com.viepovsky.user.UserService;
import io.github.viepovsky.polishutils.pesel.InvalidPeselException;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

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
        List<DiagnosticResult> retrievedResults = diagnosticResultService.getAllDiagnosticResultsByUserLogin("test");
        //Then
        assertThat(retrievedResults).isNotNull();
        assertEquals(1, retrievedResults.size());
    }

    @Test
    void should_save_DiagnosticResult() throws InvalidPeselException {
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

    @Test
    void should_get_DiagnosticResult_PDF() {
        byte[] resultResponse = {10, 1, 22, 0, 5};
        var user = User.builder().login("testuser").build();
        DiagnosticResult diagnosticResult = DiagnosticResult.builder().id(1L).user(user).resultsPdf(resultResponse).build();

        when(repository.getDiagnosticResultByIdAndUser_Login(1L, "testuser")).thenReturn(Optional.ofNullable(diagnosticResult));

        byte[] retrievedResult = diagnosticResultService.getDiagnosticResultPdfByIdAndUserLogin(1L, "testuser");
        assertEquals(resultResponse, retrievedResult);
    }

    @Test
    void should_throw_exception_when_get_DiagnosticResult_PDF_with_invalid_id() {
        when(repository.getDiagnosticResultByIdAndUser_Login(anyLong(), anyString())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> diagnosticResultService.getDiagnosticResultPdfByIdAndUserLogin(1L, "testuser"));
    }

    @Test
    void should_update_DiagnosticResult() {
        //Given
        var result = DiagnosticResult.builder().type(DiagnosticType.BLOOD).build();
        var user = new User();

        when(repository.findById(anyLong())).thenReturn(Optional.of(result));
        when(userService.getUserByLogin(anyString())).thenReturn(user);
        when(repository.save(any(DiagnosticResult.class))).thenReturn(result);
        //When
        diagnosticResultService.updateDiagnosticResult(result, "login", 5L);
        //Then
        verify(repository, times(1)).save(any(DiagnosticResult.class));
    }
}