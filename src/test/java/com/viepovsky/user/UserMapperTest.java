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
    void should_map_User_to_UserForLoginDTO() {
        //Given
        var user = User.builder().login("testLogin").password("testPassword").role(UserRole.USER).build();
        //When
        var expectedUserDTO = userMapper.mapUserToUserForLoginDTO(user);
        //Then
        assertThat(expectedUserDTO).isNotNull();
        assertEquals(user.getLogin(), expectedUserDTO.getLogin());
        assertEquals(user.getPassword(), expectedUserDTO.getPassword());
        assertEquals(user.getRole(), expectedUserDTO.getRole());
    }
}