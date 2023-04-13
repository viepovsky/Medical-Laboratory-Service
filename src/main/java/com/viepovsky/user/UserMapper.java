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
}
