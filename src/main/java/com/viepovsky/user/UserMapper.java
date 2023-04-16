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

    User mapUserCreationDtoToUser(UserCreationDTO userCreationDTO) {
        return new User(
                userCreationDTO.getLogin(),
                userCreationDTO.getPersonalId(),
                userCreationDTO.getPassword(),
                userCreationDTO.getEmail(),
                userCreationDTO.getName(),
                userCreationDTO.getLastName(),
                userCreationDTO.getPhoneNumber()
        );
    }
}
