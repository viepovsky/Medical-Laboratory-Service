package com.viepovsky.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
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
        var user = new UserDTO("testLogin", "testPassword", Role.USER);
        var json = new ObjectMapper().writeValueAsString(user);

        when(service.getUserByLogin(anyString())).thenReturn(Mockito.mock(User.class));
        when(mapper.mapToUserDtoForLogin(any(User.class))).thenReturn(user);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/medical/users")
                        .param("login", "someString"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(json));
    }

    @Test
    void should_create_user() throws Exception {
        //Given
        var userDTO = UserDTO.builder().login("testLogin").personalId("98062267819").password("testPassword22@")
                .email("email@email.com").name("test").lastName("testlast").build();
        var json = new ObjectMapper().writeValueAsString(userDTO);
        var user = User.builder().login("testLogin").personalId("98062267819").password("testPassword22@")
                .email("email@email.com").name("test").lastName("testlast").build();

        when(mapper.mapToUser(any(UserDTO.class))).thenReturn(user);
        when(service.createUser(any(User.class))).thenReturn(user);
        when(mapper.mapToCreatedUserDto(any(User.class))).thenReturn(userDTO);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/medical/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(json))
                .andExpect(MockMvcResultMatchers.header().string("Location", "/medical/users?login=testLogin"));
    }

    @Test
    void should_not_create_user_with_invalid_body_values() throws Exception {
        //Given
        var userDTO = UserDTO.builder().login("testLogin").personalId("9412").password("testPassword22@")
                .email("email").name("test").lastName("testlast").build();
        var json = new ObjectMapper().writeValueAsString(userDTO);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/medical/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void should_update_user() throws Exception {
        //Given
        var userDTO = UserDTO.builder().login("testLogin").personalId("98062267819").password("testPassword22@")
                .email("email@email.com").name("test").lastName("testlast").build();
        var json = new ObjectMapper().writeValueAsString(userDTO);
        var user = User.builder().login("testLogin").personalId("98062267819").password("testPassword22@")
                .email("email@email.com").name("test").lastName("testlast").build();

        when(mapper.mapToUser(any(UserDTO.class))).thenReturn(user);
        doNothing().when(service).updateUser(any(User.class));
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/medical/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void should_not_update_user_with_invalid_body_values() throws Exception {
        //Given
        var userDTO = UserDTO.builder().login("testLogin").personalId("9412").password("testPassword22@")
                .email("email").name("test").lastName("testlast").build();
        var json = new ObjectMapper().writeValueAsString(userDTO);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/medical/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}