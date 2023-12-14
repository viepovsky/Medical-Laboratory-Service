package com.viepovsky.user;

import com.viepovsky.exceptions.PasswordValidationException;
import com.viepovsky.user.dto.request.RegisterUserRequest;
import com.viepovsky.user.dto.request.UpdateUserRequest;
import com.viepovsky.user.dto.response.CreatedUserResponse;
import com.viepovsky.user.dto.response.DetailsUserResponse;
import io.github.viepovsky.polishutils.pesel.InvalidPeselException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
class UserFacade {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserFacade.class);
    private final UserService userService;
    private final UserMapper mapper;

    DetailsUserResponse getUserByLogin(String login) {
        LOGGER.info("Get user by login endpoint used with login:{}", login);
        var retrievedUser = userService.getUserByLogin(login);
        return mapper.mapToDetailsUserResponse(retrievedUser);
    }

    CreatedUserResponse createUser(RegisterUserRequest request) throws InvalidPeselException {
        LOGGER.info("Create user endpoint used with login:{}", request.getLogin());
        var toSave = mapper.mapToUser(request);
        var savedUser = userService.createUser(toSave);
        return mapper.mapToCreatedUserResponse(savedUser);
    }

    void updateUser(UpdateUserRequest request) throws InvalidPeselException, PasswordValidationException {
        LOGGER.info("Update user endpoint used with login:{}", request.getLogin());
        var toUpdate = mapper.mapToUser(request);
        if (Optional.ofNullable(toUpdate.getPassword()).isEmpty()) {
            userService.updateUser(toUpdate);
        } else {
            userService.updateUserWithPassword(toUpdate);
        }
    }
}
