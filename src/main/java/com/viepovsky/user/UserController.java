package com.viepovsky.user;

import com.viepovsky.exceptions.PasswordValidationException;
import com.viepovsky.user.dto.request.RegisterUserRequest;
import com.viepovsky.user.dto.request.UpdateUserRequest;
import com.viepovsky.user.dto.response.CreatedUserResponse;
import com.viepovsky.user.dto.response.DetailsUserResponse;
import com.viepovsky.utilities.LoginValidator;
import io.github.viepovsky.polishutils.pesel.InvalidPeselException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(path = "/medical/users")
@RequiredArgsConstructor
@Validated
class UserController {
    private final UserFacade userFacade;
    private final LoginValidator loginValidator;

    @GetMapping
    ResponseEntity<DetailsUserResponse> getUserByLogin(@RequestParam(name = "login") @NotBlank String login) {
        if (!loginValidator.isUserAuthorized(login)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        var userDetails = userFacade.getUserByLogin(login);
        return ResponseEntity.ok(userDetails);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    ResponseEntity<CreatedUserResponse> createUser(@RequestBody @Valid RegisterUserRequest request) {
        var result = userFacade.createUser(request);
        return ResponseEntity.created(URI.create("/medical/users?login=" + result.getLogin())).body(result);
    }

    @PutMapping
    ResponseEntity<Void> updateUser(@RequestBody @Valid UpdateUserRequest request) throws PasswordValidationException, InvalidPeselException {
        if (!loginValidator.isUserAuthorized(request.getLogin())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        userFacade.updateUser(request);
        return ResponseEntity.noContent().build();
    }
}
