package com.viepovsky.diagnose;

import com.viepovsky.diagnose.dto.DiagnosticResultRequest;
import com.viepovsky.diagnose.dto.DiagnosticResultResponse;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class DiagnosticResultMapperTest {
    private final DiagnosticResultMapper mapper = new DiagnosticResultMapper();

    @Test
    void should_map_DiagnosticResult_to_DiagnosticResultResponse() {
        //Given
        var result = DiagnosticResult.builder().type(DiagnosticType.BLOOD).status(DiagnosticStatus.AWAITING_RESULT)
                .registration(LocalDateTime.now()).build();
        //When
        var retrievedResult = mapper.mapToDiagnosticResultResponse(result);
        //Then
        assertThat(retrievedResult).isNotNull();
        assertEquals(result.getType().toString(), retrievedResult.getType());
        assertEquals(result.getRegistration(), retrievedResult.getRegistration());
    }

    @Test
    void should_map_DiagnosticResult_list_to_DiagnosticResultResponse_list() {
        //Given
        var result = DiagnosticResult.builder().type(DiagnosticType.BLOOD).status(DiagnosticStatus.AWAITING_RESULT)
                .registration(LocalDateTime.now()).build();
        List<DiagnosticResult> results = new ArrayList<>(List.of(result));
        //When
        List<DiagnosticResultResponse> retrievedResults = mapper.mapToDiagnosticResultResponseList(results);
        //Then
        assertThat(retrievedResults).isNotNull();
        assertEquals(1, retrievedResults.size());
    }

    @Test
    void should_map_DiagnosticResultRequest_to_DiagnosticResult() {
        //Given
        var result = DiagnosticResultRequest.builder().type(DiagnosticType.BLOOD)
                .registration(LocalDateTime.now()).build();
        //When
        var retrievedResult = mapper.mapToDiagnosticResult(result);
        //Then
        assertThat(retrievedResult).isNotNull();
        assertEquals(result.getType(), retrievedResult.getType());
    }
}