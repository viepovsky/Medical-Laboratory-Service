package com.viepovsky.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viepovsky.security.dto.AuthenticationResponse;
import com.viepovsky.user.dto.request.AuthenticationUserRequest;
import com.viepovsky.user.dto.request.RegisterUserRequest;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = "spring.config.additional-location=classpath:application-test.yml")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class AuthenticationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationService service;

    @Test
    void should_get_token_after_registering_user() throws Exception {
        //Given
        var request = RegisterUserRequest.builder()
                .login("testlogin")
                .personalId("57040433312")
                .password("TestPassword20@")
                .email("test@mail.com")
                .firstName("name")
                .lastName("lastname")
                .phoneNumber("777777777")
                .build();
        var jsonRequest = new ObjectMapper().writeValueAsString(request);
        var response = new AuthenticationResponse("token");
        var jsonResponse = new ObjectMapper().writeValueAsString(response);
        when(service.register(any(RegisterUserRequest.class))).thenReturn(response);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/medical/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(jsonResponse));
    }

    @Test
    void should_get_token_after_authenticating_user() throws Exception {
        //Given
        var request = AuthenticationUserRequest.builder()
                .login("testlogin").password("TestPassword20@").build();
        var jsonRequest = new ObjectMapper().writeValueAsString(request);
        var response = new AuthenticationResponse("token");
        var jsonResponse = new ObjectMapper().writeValueAsString(response);
        when(service.authenticate(any(AuthenticationUserRequest.class))).thenReturn(response);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/medical/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(jsonResponse));
    }
}