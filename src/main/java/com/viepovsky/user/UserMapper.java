package com.viepovsky.user;

import org.springframework.stereotype.Service;

@Service
class UserMapper {
    UserForLoginDTO mapUserToUserForLoginDTO(User user) {
        return new UserForLoginDTO(
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
}
