package com.viepovsky.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viepovsky.exceptions.PasswordValidationException;
import com.viepovsky.user.dto.request.RegisterUserRequest;
import com.viepovsky.user.dto.request.UpdateUserRequest;
import com.viepovsky.user.dto.response.CreatedUserResponse;
import com.viepovsky.user.dto.response.DetailsUserResponse;
import com.viepovsky.utilities.LoginValidator;
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
import java.util.Date;
import java.util.HashMap;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = "spring.config.additional-location=classpath:application-test.yml")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserFacade facade;

    @MockBean
    private LoginValidator validator;
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
    void should_get_user_details() throws Exception {
        //Given
        var userInDb = User.builder().login("testLogin").role(Role.ROLE_USER).build();
        var userResponse = DetailsUserResponse.builder().login("testLogin").firstName("testName").build();
        var jsonResponse = new ObjectMapper().writeValueAsString(userResponse);
        var jwtToken = generateToken("testLogin", secretKey);

        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(userInDb);
        when(validator.isUserAuthorized(anyString())).thenReturn(true);
        when(facade.getUserByLogin(anyString())).thenReturn(userResponse);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/medical/users")
                        .param("login", "testLogin")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(jsonResponse));
    }

    @Test
    void should_not_get_user_details_if_token_login_doesnt_match_with_given_login() throws Exception {
        //Given
        var userInDb = User.builder().login("testLogin22").role(Role.ROLE_USER).build();
        var jwtToken = generateToken("testLogin22", secretKey);

        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(userInDb);
        when(validator.isUserAuthorized(anyString())).thenReturn(false);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/medical/users")
                        .param("login", "testLogin")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isForbidden());
    }

    @Test
    void should_create_user() throws Exception {
        //Given
        var adminInDb = User.builder().login("testAdmin").role(Role.ROLE_ADMIN).build();
        var jwtToken = generateToken("testAdmin", secretKey);
        var userRequest = RegisterUserRequest.builder().login("testLogin").personalId("98062267819").password("testPassword22@")
                .email("email@email.com").firstName("test").lastName("testlast").build();
        var jsonRequest = new ObjectMapper().writeValueAsString(userRequest);
        var userResponse = CreatedUserResponse.builder().login("testLogin").id(5L).build();
        var jsonResponse = new ObjectMapper().writeValueAsString(userResponse);

        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(adminInDb);
        when(facade.createUser(any(RegisterUserRequest.class))).thenReturn(userResponse);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/medical/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(jsonResponse))
                .andExpect(MockMvcResultMatchers.header().string("Location", "/medical/users?login=testLogin"));
    }

    @Test
    void should_not_create_user_with_invalid_body_values() throws Exception {
        //Given
        var adminInDb = User.builder().login("testAdmin").role(Role.ROLE_ADMIN).build();
        var jwtToken = generateToken("testAdmin", secretKey);
        var userRequest = RegisterUserRequest.builder().login("testLogin").personalId("98062267819").password("testPassword")
                .email("email").firstName("test").lastName("testlast").build();
        var jsonRequest = new ObjectMapper().writeValueAsString(userRequest);

        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(adminInDb);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/medical/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_not_create_user_if_token_is_from_user_with_UserRole() throws Exception {
        //Given
        var userInDb = User.builder().login("testUser").role(Role.ROLE_USER).build();
        var jwtToken = generateToken("testUser", secretKey);
        var userRequest = RegisterUserRequest.builder().login("testLogin").personalId("98062267819").password("testPassword22@")
                .email("email@email.com").firstName("test").lastName("testlast").build();
        var jsonRequest = new ObjectMapper().writeValueAsString(userRequest);

        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(userInDb);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/medical/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isForbidden());
    }

    @Test
    void should_update_user() throws Exception, PasswordValidationException {
        //Given
        var user = User.builder().login("testLogin").personalId("98062267819").password("testPassword22@")
                .email("email@email.com").name("test").lastName("testlast").role(Role.ROLE_USER).build();
        var jwtToken = generateToken("testLogin", secretKey);
        var userRequest = UpdateUserRequest.builder().login("testLogin").personalId("98062267819").password("testPassword22@")
                .email("email@email.com").firstName("test").lastName("testlast").build();
        var jsonRequest = new ObjectMapper().writeValueAsString(userRequest);

        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(user);
        when(validator.isUserAuthorized(anyString())).thenReturn(true);
        doNothing().when(facade).updateUser(any(UpdateUserRequest.class));
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/medical/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isNoContent());
    }

    @Test
    void should_not_update_user_if_token_login_doesnt_match_with_given_login() throws Exception {
        //Given
        var userInDb = User.builder().login("testLogin22").role(Role.ROLE_USER).build();
        var jwtToken = generateToken("testLogin22", secretKey);
        var userRequest = UpdateUserRequest.builder().login("testLogin").personalId("98062267819").password("testPassword22@")
                .email("email@email.com").firstName("test").lastName("testlast").build();
        var jsonRequest = new ObjectMapper().writeValueAsString(userRequest);

        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(userInDb);
        when(validator.isUserAuthorized(anyString())).thenReturn(false);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/medical/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isForbidden());
    }

    @Test
    void should_not_update_user_with_invalid_body_values() throws Exception {
        //Given
        var userInDb = User.builder().login("testLogin").role(Role.ROLE_USER).build();
        var jwtToken = generateToken("testLogin", secretKey);
        var userRequest = UpdateUserRequest.builder().login("testLogin").personalId("98062267819").password("testPassword")
                .email("email").firstName("test").lastName("testlast").build();
        var jsonRequest = new ObjectMapper().writeValueAsString(userRequest);

        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(userInDb);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/medical/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isBadRequest());
    }
}