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
    private static final Logger logger = LoggerFactory.getLogger(UserFacade.class);
    private final UserService service;
    private final UserMapper mapper;

    DetailsUserResponse getUserByLogin(String login) {
        logger.info("getUserByLogin endpoint used with login value: " + login);
        var retrievedUser = service.getUserByLogin(login);
        return mapper.mapToDetailsUserResponse(retrievedUser);
    }

    CreatedUserResponse createUser(RegisterUserRequest request) throws InvalidPeselException {
        logger.info("createUser endpoint used");
        var toSave = mapper.mapToUser(request);
        var savedUser = service.createUser(toSave);
        return mapper.mapToCreatedUserResponse(savedUser);
    }

    void updateUser(UpdateUserRequest request) throws InvalidPeselException, PasswordValidationException {
        logger.info("updateUser endpoint used");
        var toUpdate = mapper.mapToUser(request);
        if (Optional.ofNullable(toUpdate.getPassword()).isEmpty()) {
            service.updateUser(toUpdate);
        } else {
            service.updateUserWithPassword(toUpdate);
        }
    }
}
