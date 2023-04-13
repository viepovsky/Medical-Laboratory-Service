package com.viepovsky.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@WebMvcTest(UserController.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService service;

    @MockBean
    private UserMapper mapper;

    @Test
    void should_get_user_for_login_purposes() throws Exception {
        //Given
        UserForLoginDTO expectedUser = new UserForLoginDTO("testLogin", "testPassword", UserRole.USER);
        var expectedJson = new ObjectMapper().writeValueAsString(expectedUser);

        when(service.findUserByLogin(anyString())).thenReturn(Mockito.mock(User.class));
        when(mapper.mapUserToUserForLoginDTO(any(User.class))).thenReturn(expectedUser);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/medical/user")
                        .param("login", anyString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedJson));
    }

    //TODO: I will do it later
    @Test
    void should_not_get_user_for_login_purposes_that_doesnt_exist() {
        //Given
        //When
        //Then
    }
}