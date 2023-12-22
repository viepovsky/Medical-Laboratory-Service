package com.viepovsky.diagnose;

import com.viepovsky.diagnose.dto.DiagnosticResultRequest;
import com.viepovsky.diagnose.dto.DiagnosticResultResponse;
import io.github.viepovsky.polishutils.pesel.InvalidPeselException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class DiagnosticResultFacadeTest {
    @InjectMocks
    private DiagnosticResultFacade facade;

    @Mock
    private DiagnosticResultService service;

    @Mock
    private DiagnosticResultMapper mapper;

    @Test
    void should_get_all_DiagnosticResults_for_given_login() {
        //Given
        var result = DiagnosticResult.builder().build();
        List<DiagnosticResult> results = new ArrayList<>(List.of(result));
        var responseResult = DiagnosticResultResponse.builder().build();
        List<DiagnosticResultResponse> responseResults = new ArrayList<>(List.of(responseResult));

        when(service.getAllDiagnosticResultsByUserLogin(anyString())).thenReturn(results);
        when(mapper.mapToDiagnosticResultResponseList(anyList())).thenReturn(responseResults);
        //When
        List<DiagnosticResultResponse> retrievedResponse = facade.getAllDiagnosticResults("login");
        //Then
        assertEquals(responseResults, retrievedResponse);
    }

    @Test
    void should_get_DiagnosticResults_PDF() {
        byte[] resultResponse = {10, 1, 22, 0, 5};
        when(service.getDiagnosticResultPdfByIdAndUserLogin(anyLong(), anyString())).thenReturn(resultResponse);

        byte[] retrievedResponse = facade.getDiagnosticResultPdf(1L, "testlogin");

        assertEquals(resultResponse, retrievedResponse);
    }

    @Test
    void should_create_DiagnosticResult() {
        //Given
        var resultRequest = DiagnosticResultRequest.builder().status(DiagnosticStatus.AWAITING_RESULT).registration(LocalDateTime.now()).userLogin("test").type(DiagnosticType.BLOOD).resultsPdf(new byte[]{}).build();
        var result = DiagnosticResult.builder().build();
        var resultResponse = DiagnosticResultResponse.builder().status("AWAITING_RESULT").type("BLOOD").build();

        when(mapper.mapToDiagnosticResult(any(DiagnosticResultRequest.class))).thenReturn(result);
        when(service.saveDiagnosticResult(any(DiagnosticResult.class), anyString())).thenReturn(result);
        when(mapper.mapToDiagnosticResultResponse(any(DiagnosticResult.class))).thenReturn(resultResponse);
        //When
        var retrievedResponse = facade.createDiagnosticResult(resultRequest);
        //Then
        assertNotNull(retrievedResponse);
    }

    @Test
    void should_update_DiagnosticResult() {
        //Given
        var resultRequest = DiagnosticResultRequest.builder().status(DiagnosticStatus.AWAITING_RESULT).registration(LocalDateTime.now()).userLogin("test").type(DiagnosticType.BLOOD).resultsPdf(new byte[]{}).build();
        var result = DiagnosticResult.builder().build();

        when(mapper.mapToDiagnosticResult(any(DiagnosticResultRequest.class))).thenReturn(result);
        doNothing().when(service).updateDiagnosticResult(any(DiagnosticResult.class), anyString(), anyLong());
        //When
        facade.updateDiagnosticResult(resultRequest, 5L);
        //Then
        verify(service, times(1)).updateDiagnosticResult(any(DiagnosticResult.class), anyString(), anyLong());
    }
}