package com.viepovsky.user;

import org.springframework.stereotype.Service;

@Service
class UserMapper {
    UserDTO mapToUserDtoForLogin(User user) {
        return new UserDTO(
                user.getLogin(),
                user.getPassword(),
                user.getRole()
        );
    }

    User mapToUser(UserDTO userDTO) {
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

    UserDTO mapToCreatedUserDto(User user) {
        return new UserDTO(
                user.getId(),
                user.getLogin());
    }
}
