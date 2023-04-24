package com.viepovsky.user;

import com.viepovsky.exceptions.PasswordValidationException;
import com.viepovsky.user.dto.request.UpdateUserRequest;
import com.viepovsky.user.dto.response.CreatedUserResponse;
import com.viepovsky.user.dto.request.RegisterUserRequest;
import com.viepovsky.user.dto.response.UserDetailsResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping(path = "/medical/users")
@RequiredArgsConstructor
@Validated
class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService service;
    private final UserMapper mapper;

    @GetMapping
    ResponseEntity<UserDetailsResponse> getUserByLogin(@RequestParam(name = "login") @NotBlank String login) {
        String loginFromToken = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!loginFromToken.equals(login)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        logger.info("getUserByLogin endpoint used with login value: " + login);
        var userDetails = mapper.mapToUserDetailsResponse(service.getUserByLogin(login));
        return ResponseEntity.ok(userDetails);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    ResponseEntity<CreatedUserResponse> createUser(@RequestBody @Valid RegisterUserRequest request) {
        logger.info("createUser endpoint used with body: " + request.toString());
        User toSave = mapper.mapToUser(request);
        CreatedUserResponse result = mapper.mapToCreatedUserResponse(service.createUser(toSave));
        return ResponseEntity.created(URI.create("/medical/users?login=" + result.getLogin())).body(result);
    }

    @PutMapping
    ResponseEntity<Void> updateUser(@RequestBody @Valid UpdateUserRequest request) throws PasswordValidationException {
        String loginFromToken = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!loginFromToken.equals(request.getLogin())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        User user = mapper.mapToUser(request);
        if (Optional.ofNullable(user.getPassword()).isEmpty()) {
            service.updateUser(user);
        } else {
            service.updateUserWithPassword(user);
        }
        return ResponseEntity.noContent().build();
    }
}
