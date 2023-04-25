package com.viepovsky.diagnose;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viepovsky.user.Role;
import com.viepovsky.user.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
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

import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = "spring.config.additional-location=classpath:application-test.yml")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class DiagnosticResultControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DiagnosticResultService service;

    @MockBean
    private DiagnosticResultMapper mapper;

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
    void should_get_all_DiagnosticResults_for_given_login() throws Exception {
        //Given
        var userInDb = User.builder().login("testLogin").role(Role.ROLE_USER).build();
        var jwtToken = generateToken("testLogin", secretKey);
        var result = DiagnosticResult.builder().build();
        List<DiagnosticResult> results = new ArrayList<>(List.of(result));
        var resultDTO = DiagnosticResultDTO.builder().build();
        List<DiagnosticResultDTO> resultsDTO = new ArrayList<>(List.of(resultDTO));
        var json = new ObjectMapper().writeValueAsString(resultsDTO);

        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(userInDb);
        when(service.getAllDiagnosticResults(anyString())).thenReturn(results);
        when(mapper.mapToDiagnosticResultDtoList(results)).thenReturn(resultsDTO);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/medical/results")
                        .param("login", "testLogin")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(json));
    }

    @Test
    void should_not_get_all_DiagnosticResults_if_token_login_doesnt_match_with_given_login() throws Exception {
        //Given
        var userInDb = User.builder().login("testLogin22").role(Role.ROLE_USER).build();
        var jwtToken = generateToken("testLogin", secretKey);

        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(userInDb);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/medical/results")
                        .param("login", "testLogin")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    void should_create_DiagnosticResult() throws Exception {
        //Given
        var adminInDb = User.builder().login("testAdmin").role(Role.ROLE_ADMIN).build();
        var jwtToken = generateToken("testAdmin", secretKey);
        var resultRequest = DiagnosticResultDTO.builder().userLogin("test").type(DiagnosticType.BLOOD).resultsPdf(new byte[]{}).build();
        var result = DiagnosticResult.builder().build();
        var jsonRequest = new ObjectMapper().writeValueAsString(resultRequest);

        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(adminInDb);
        when(mapper.mapToDiagnosticResult(any(DiagnosticResultDTO.class))).thenReturn(result);
        when(service.saveDiagnosticResult(any(DiagnosticResult.class), anyString())).thenReturn(result);
        when(mapper.mapToDiagnosticResultDto(any(DiagnosticResult.class))).thenReturn(resultRequest);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/medical/results")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(jsonRequest))
                .andExpect(MockMvcResultMatchers.header().string("Location", "/medical/results?login=test"));
    }
}