package com.viepovsky.user;

import com.viepovsky.user.dto.request.UpdateUserRequest;
import com.viepovsky.user.dto.response.CreatedUserResponse;
import com.viepovsky.user.dto.request.RegisterUserRequest;
import com.viepovsky.user.dto.response.DetailsUserResponse;
import org.springframework.stereotype.Service;

@Service
class UserMapper {
    DetailsUserResponse mapToDetailsUserResponse(User user) {
        return new DetailsUserResponse(
                user.getId(),
                user.getLogin(),
                user.getPersonalId(),
                user.getEmail(),
                user.getName(),
                user.getLastName(),
                user.getPhoneNumber()
        );
    }

    CreatedUserResponse mapToCreatedUserResponse(User user) {
        return new CreatedUserResponse(
                user.getId(),
                user.getLogin()
        );
    }

    User mapToUser(RegisterUserRequest request) {
        return new User(
                request.getLogin(),
                request.getPersonalId(),
                request.getPassword(),
                request.getEmail(),
                request.getFirstName(),
                request.getLastName(),
                request.getPhoneNumber()
        );
    }

    User mapToUser(UpdateUserRequest request) {
        return new User(
                request.getLogin(),
                request.getPersonalId(),
                request.getPassword(),
                request.getEmail(),
                request.getFirstName(),
                request.getLastName(),
                request.getPhoneNumber()
        );
    }
}
