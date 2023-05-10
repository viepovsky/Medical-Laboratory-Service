package com.viepovsky.examination;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viepovsky.examination.dto.ExaminationRequest;
import com.viepovsky.examination.dto.ExaminationResponse;
import com.viepovsky.user.Role;
import com.viepovsky.user.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = "spring.config.additional-location=classpath:application-test.yml")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ExaminationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExaminationService service;

    @MockBean
    private ExaminationMapper mapper;

    @MockBean
    private UserDetailsService userDetailsService;

    @Value("${jwt.secret.key}")
    private String secretKey;

    public static String generateToken(String username, String secretKey) {
        return Jwts
                .builder()
                .setClaims(new HashMap<>())
                .setSubject(username)
                .setIssuer("medical-app.com")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(getSignInKey(secretKey), SignatureAlgorithm.HS256)
                .compact();
    }

    private static Key getSignInKey(String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Test
    void should_get_all_Examination() throws Exception {
        //Given
        List<Examination> examinations = List.of();
        ExaminationResponse response = new ExaminationResponse();
        List<ExaminationResponse> responses = List.of(response);
        String responsesJson = new ObjectMapper().writeValueAsString(responses);

        when(service.getAllExaminations()).thenReturn(examinations);
        when(mapper.mapToExaminationResponseList(anyList())).thenReturn(responses);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/medical/examinations"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(responsesJson));
    }

    @Test
    void should_get_Examination_by_id() throws Exception {
        //Given
        Examination examination = new Examination();
        ExaminationResponse response = new ExaminationResponse();
        String responseJson = new ObjectMapper().writeValueAsString(response);

        when(service.getExamination(anyLong())).thenReturn(examination);
        when(mapper.mapToExaminationResponse(any(Examination.class))).thenReturn(response);
        //When && then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/medical/examinations/5"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(responseJson));

    }

    @Test
    void should_create_examination() throws Exception {
        //Given
        var adminInDb = User.builder().login("testAdmin").role(Role.ROLE_ADMIN).build();
        String jwtToken = generateToken("testAdmin", secretKey);
        ExaminationRequest request = ExaminationRequest.builder().name("test").type(ExaminationType.BLOOD)
                .shortDescription("shortDesc").cost(BigDecimal.valueOf(50)).build();
        String requestJson = new ObjectMapper().writeValueAsString(request);
        ExaminationResponse response = ExaminationResponse.builder().id(5L).name("test").type(ExaminationType.BLOOD)
                .shortDescription("shortDesc").cost(BigDecimal.valueOf(50)).build();
        String responseJson = new ObjectMapper().writeValueAsString(response);

        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(adminInDb);
        when(mapper.mapToExamination(any())).thenReturn(Mockito.mock(Examination.class));
        when(service.saveExamination(any())).thenReturn(Mockito.mock(Examination.class));
        when(mapper.mapToExaminationResponse(any())).thenReturn(response);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/medical/examinations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(responseJson));
    }

    @Test
    void should_update_examination() throws Exception {
        //Given
        var adminInDb = User.builder().login("testAdmin").role(Role.ROLE_ADMIN).build();
        String jwtToken = generateToken("testAdmin", secretKey);
        ExaminationRequest request = ExaminationRequest.builder().name("test").type(ExaminationType.BLOOD)
                .shortDescription("shortDesc").cost(BigDecimal.valueOf(50)).build();
        String requestJson = new ObjectMapper().writeValueAsString(request);

        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(adminInDb);
        when(mapper.mapToExamination(any())).thenReturn(Mockito.mock(Examination.class));
        doNothing().when(service).updateExamination(any());
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/medical/examinations/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void should_delete_examination() throws Exception {
        //Given
        var adminInDb = User.builder().login("testAdmin").role(Role.ROLE_ADMIN).build();
        String jwtToken = generateToken("testAdmin", secretKey);

        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(adminInDb);
        doNothing().when(service).deleteExamination(any());
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/medical/examinations/5")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void should_not_delete_examination_if_role_is_user() throws Exception {
        //Given
        var adminInDb = User.builder().login("testUser").role(Role.ROLE_USER).build();
        String jwtToken = generateToken("testUser", secretKey);

        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(adminInDb);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/medical/examinations/5")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

}