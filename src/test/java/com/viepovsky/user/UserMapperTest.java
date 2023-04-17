package com.viepovsky.user;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class UserMapperTest {
    private final UserMapper userMapper = new UserMapper();
    @Test
    void should_map_User_to_UserDTO_for_login() {
        //Given
        var user = User.builder().login("testLogin").password("testPassword").role(UserRole.USER).build();
        //When
        var expectedUserDTO = userMapper.mapToUserDtoForLogin(user);
        //Then
        assertThat(expectedUserDTO).isNotNull();
        assertEquals(user.getLogin(), expectedUserDTO.getLogin());
        assertEquals(user.getPassword(), expectedUserDTO.getPassword());
        assertEquals(user.getRole(), expectedUserDTO.getRole());
    }

    @Test
    void should_map_UserDTO_to_User() {
        //Given
        var userDTO = UserDTO.builder().login("testLogin").personalId("testId").password("testPassword")
                .email("test@Email.com").name("testName").lastName("lastName").phoneNumber("testNumber").build();
        //When
        var expectedUser = userMapper.mapToUser(userDTO);
        //Then
        assertThat(expectedUser).isNotNull();
        assertEquals(userDTO.getLogin(), expectedUser.getLogin());
        assertEquals(userDTO.getEmail(), expectedUser.getEmail());
        assertEquals(userDTO.getPhoneNumber(), expectedUser.getPhoneNumber());
    }

    @Test
    void should_map_User_to_created_UserDTO() {
        //Given
        var user = User.builder().login("testLogin").id(52L).build();
        //When
        var expectedUserDTO = userMapper.mapToCreatedUserDto(user);
        //Then
        assertThat(expectedUserDTO).isNotNull();
        assertEquals(user.getLogin(), expectedUserDTO.getLogin());
        assertEquals(user.getId(), expectedUserDTO.getId());
    }
}