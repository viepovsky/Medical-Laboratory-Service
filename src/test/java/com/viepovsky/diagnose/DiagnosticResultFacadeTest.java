package com.viepovsky.diagnose;

import com.viepovsky.diagnose.dto.DiagnosticResultRequest;
import com.viepovsky.diagnose.dto.DiagnosticResultResponse;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class DiagnosticResultFacadeTest {
    @InjectMocks
    private DiagnosticResultFacade facade;

    @Mock
    private DiagnosticResultService service;

    @Mock
    private DiagnosticResultMapper mapper;

    @Mock
    private LoginValidator validator;

    @Test
    void should_get_all_DiagnosticResults_for_given_login() {
        //Given
        var result = DiagnosticResult.builder().build();
        List<DiagnosticResult> results = new ArrayList<>(List.of(result));
        var responseResult = DiagnosticResultResponse.builder().build();
        List<DiagnosticResultResponse> responseResults = new ArrayList<>(List.of(responseResult));

        when(validator.isUserAuthorized(anyString())).thenReturn(true);
        when(service.getAllDiagnosticResults(anyString())).thenReturn(results);
        when(mapper.mapToDiagnosticResultResponseList(anyList())).thenReturn(responseResults);
        //When
        ResponseEntity<List<DiagnosticResultResponse>> retrievedResponse = facade.getAllDiagnosticResults("login");
        //Then
        assertEquals(responseResults, retrievedResponse.getBody());
        assertEquals(HttpStatus.OK, retrievedResponse.getStatusCode());
    }

    @Test
    void should_not_get_all_DiagnosticResults_if_token_login_doesnt_match_with_given_login() {
        //Given
        when(validator.isUserAuthorized(anyString())).thenReturn(false);
        //When
        ResponseEntity<List<DiagnosticResultResponse>> retrievedResponse = facade.getAllDiagnosticResults("login");
        //Then
        assertNull(retrievedResponse.getBody());
        assertEquals(HttpStatus.FORBIDDEN, retrievedResponse.getStatusCode());
    }

    @Test
        void should_create_DiagnosticResult() {
        //Given
        var resultRequest = DiagnosticResultRequest.builder().status(DiagnosticStatus.AWAITING_RESULT).registration(LocalDateTime.now()).userLogin("test").type(DiagnosticType.BLOOD).resultsPdf(new byte[]{}).build();
        var result = DiagnosticResult.builder().build();
        var resultResponse = DiagnosticResultResponse.builder().status("AWAITING_RESULT").type("BLOOD").resultsPdf(new byte[]{}).build();

        when(mapper.mapToDiagnosticResult(any(DiagnosticResultRequest.class))).thenReturn(result);
        when(service.saveDiagnosticResult(any(DiagnosticResult.class), anyString())).thenReturn(result);
        when(mapper.mapToDiagnosticResultResponse(any(DiagnosticResult.class))).thenReturn(resultResponse);
        //When
        ResponseEntity<DiagnosticResultResponse> retrievedResponse = facade.createDiagnosticResult(resultRequest);
        //Then
        assertNotNull(retrievedResponse.getBody());
        assertEquals(HttpStatus.CREATED, retrievedResponse.getStatusCode());
        assertEquals("/medical/results?login=test", retrievedResponse.getHeaders().getLocation().toString());
}
}