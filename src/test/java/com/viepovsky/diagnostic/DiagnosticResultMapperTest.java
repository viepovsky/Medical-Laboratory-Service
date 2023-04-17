package com.viepovsky.diagnostic;

import com.viepovsky.user.User;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class DiagnosticResultMapperTest {
    private final DiagnosticResultMapper mapper = new DiagnosticResultMapper();

    @Test
    void should_map_DiagnosticResult_to_DiagnosticResultDTO() {
        //Given
        var user = User.builder().login("test").build();
        var result = DiagnosticResult.builder().type(DiagnosticType.BLOOD)
                .registration(LocalDateTime.now()).user(user).build();
        //When
        var retrievedResultDTO = mapper.mapToDiagnosticResultDto(result);
        //Then
        assertThat(retrievedResultDTO).isNotNull();
        assertEquals(result.getType(), retrievedResultDTO.getType());
        assertEquals(result.getRegistration(), retrievedResultDTO.getRegistration());
    }

    @Test
    void should_map_DiagnosticResult_list_to_DiagnosticResultDTO_list() {
        //Given
        var user = User.builder().login("test").build();
        var result = DiagnosticResult.builder().type(DiagnosticType.BLOOD)
                .registration(LocalDateTime.now()).user(user).build();
        List<DiagnosticResult> results = new ArrayList<>(List.of(result));
        //When
        List<DiagnosticResultDTO> retrievedResultsDto = mapper.mapToDiagnosticResultDtoList(results);
        //Then
        assertThat(retrievedResultsDto).isNotNull();
        assertEquals(1, retrievedResultsDto.size());
    }

    @Test
    void should_map_DiagnosticResultDTO_to_DiagnosticResult() {
        //Given
        var resultDTO = DiagnosticResultDTO.builder().type(DiagnosticType.BLOOD)
                .registration(LocalDateTime.now()).build();
        //When
        var retrievedResult = mapper.mapToDiagnosticResult(resultDTO);
        //Then
        assertThat(retrievedResult).isNotNull();
        assertEquals(resultDTO.getType(), retrievedResult.getType());
    }
}