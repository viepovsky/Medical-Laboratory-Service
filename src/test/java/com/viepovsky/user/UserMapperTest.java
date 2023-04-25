package com.viepovsky.user;

import com.viepovsky.user.dto.request.RegisterUserRequest;
import com.viepovsky.user.dto.request.UpdateUserRequest;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class UserMapperTest {
    private final UserMapper mapper = new UserMapper();
    @Test
    void should_map_User_to_UserDetailsResponse() {
        //Given
        var user = User.builder().login("testLogin").name("test").build();
        //When
        var retrievedResponse = mapper.mapToUserDetailsResponse(user);
        //Then
        assertThat(retrievedResponse).isNotNull();
        assertEquals(user.getLogin(), retrievedResponse.getLogin());
        assertEquals(user.getName(), retrievedResponse.getFirstName());
    }

    @Test
    void should_map_User_to_UserCreatedResponse() {
        //Given
        var user = User.builder().login("testLogin").id(5L).personalId("testId").email("test@Email.com")
                .name("testName").lastName("lastName").phoneNumber("testNumber").build();
        //When
        var retrievedResponse = mapper.mapToCreatedUserResponse(user);
        //Then
        assertThat(retrievedResponse).isNotNull();
        assertEquals(user.getLogin(), retrievedResponse.getLogin());
        assertEquals(user.getId(), retrievedResponse.getId());
    }

    @Test
    void should_map_RegisterUserRequest_to_User() {
        //Given
        var user = RegisterUserRequest.builder().login("testLogin").personalId("testId").email("test@Email.com")
                .firstName("testName").lastName("lastName").phoneNumber("testNumber").build();
        //When
        var retrievedUser = mapper.mapToUser(user);
        //Then
        assertThat(retrievedUser).isNotNull();
        assertEquals(user.getLogin(), retrievedUser.getLogin());
        assertEquals(user.getPersonalId(), retrievedUser.getPersonalId());
    }

    @Test
    void should_map_UpdateUserRequest_to_User() {
        //Given
        var user = UpdateUserRequest.builder().login("testLogin").personalId("testId").email("test@Email.com")
                .firstName("testName").lastName("lastName").phoneNumber("testNumber").build();
        //When
        var retrievedUser = mapper.mapToUser(user);
        //Then
        assertThat(retrievedUser).isNotNull();
        assertEquals(user.getLogin(), retrievedUser.getLogin());
        assertEquals(user.getPersonalId(), retrievedUser.getPersonalId());
    }
}