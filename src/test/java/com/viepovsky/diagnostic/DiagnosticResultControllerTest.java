package com.viepovsky.diagnostic;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@WebMvcTest(DiagnosticResultController.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class DiagnosticResultControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DiagnosticResultService service;

    @MockBean
    private DiagnosticResultMapper mapper;

    @Test
    void should_get_all_DiagnosticResults_for_given_login() throws Exception {
        //Given
        var result = DiagnosticResult.builder().build();
        List<DiagnosticResult> results = new ArrayList<>(List.of(result));
        var resultDTO = DiagnosticResultDTO.builder().build();
        List<DiagnosticResultDTO> resultsDTO = new ArrayList<>(List.of(resultDTO));
        var json = new ObjectMapper().writeValueAsString(resultsDTO);

        when(service.getAllDiagnosticResults(anyString())).thenReturn(results);
        when(mapper.mapToDiagnosticResultDtoList(results)).thenReturn(resultsDTO);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/medical/results")
                        .param("login", "test"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(json));
    }

    @Test
    void should_create_DiagnosticResult() throws Exception {
        //Given
        var result = DiagnosticResult.builder().build();
        var resultDTO = DiagnosticResultDTO.builder().userLogin("test").type(DiagnosticType.BLOOD).resultsPdf(new byte[]{}).build();
        var json = new ObjectMapper().writeValueAsString(resultDTO);

        when(mapper.mapToDiagnosticResult(any(DiagnosticResultDTO.class))).thenReturn(result);
        when(service.saveDiagnosticResult(any(DiagnosticResult.class), anyString())).thenReturn(result);
        when(mapper.mapToDiagnosticResultDto(any(DiagnosticResult.class))).thenReturn(resultDTO);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/medical/results")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(json))
                .andExpect(MockMvcResultMatchers.header().string("Location", "/medical/results?login=test"));
    }
}