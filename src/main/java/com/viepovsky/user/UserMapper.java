package com.viepovsky.user;

import org.springframework.stereotype.Service;

@Service
class UserMapper {
    UserDTO mapUserToUserDtoForLogin(User user) {
        return new UserDTO(
                user.getLogin(),
                user.getPassword(),
                user.getRole()
        );
    }

    User mapUserDtoToUser(UserDTO userDTO) {
        return new User(
                userDTO.getLogin(),
                userDTO.getPersonalId(),
                userDTO.getPassword(),
                userDTO.getEmail(),
                userDTO.getName(),
                userDTO.getLastName(),
                userDTO.getPhoneNumber()
        );
    }

    UserDTO mapUserToCreatedUserDto(User user) {
        return new UserDTO(
                user.getId(),
                user.getLogin());
    }
}
