package com.viepovsky.user;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class UserMapperTest {
    private final UserMapper mapper = new UserMapper();
    @Test
    void should_map_User_to_UserDTO_for_login() {
        //Given
        var user = User.builder().login("testLogin").password("testPassword").role(Role.USER).build();
        //When
        var retrievedUserDTO = mapper.mapToUserDtoForLogin(user);
        //Then
        assertThat(retrievedUserDTO).isNotNull();
        assertEquals(user.getLogin(), retrievedUserDTO.getLogin());
        assertEquals(user.getPassword(), retrievedUserDTO.getPassword());
        assertEquals(user.getRole(), retrievedUserDTO.getRole());
    }

    @Test
    void should_map_UserDTO_to_User() {
        //Given
        var userDTO = UserDTO.builder().login("testLogin").personalId("testId").password("testPassword")
                .email("test@Email.com").name("testName").lastName("lastName").phoneNumber("testNumber").build();
        //When
        var retrievedUser = mapper.mapToUser(userDTO);
        //Then
        assertThat(retrievedUser).isNotNull();
        assertEquals(userDTO.getLogin(), retrievedUser.getLogin());
        assertEquals(userDTO.getEmail(), retrievedUser.getEmail());
        assertEquals(userDTO.getPhoneNumber(), retrievedUser.getPhoneNumber());
    }

    @Test
    void should_map_User_to_UserDTO_for_creating_user_account() {
        //Given
        var user = User.builder().login("testLogin").id(52L).build();
        //When
        var retrievedUserDTO = mapper.mapToCreatedUserDto(user);
        //Then
        assertThat(retrievedUserDTO).isNotNull();
        assertEquals(user.getLogin(), retrievedUserDTO.getLogin());
        assertEquals(user.getId(), retrievedUserDTO.getId());
    }
}