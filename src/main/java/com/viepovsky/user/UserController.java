package com.viepovsky.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(path = "/medical/users")
@RequiredArgsConstructor
@Validated
class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService service;
    private final UserMapper mapper;

    @GetMapping
    ResponseEntity<UserDTO> getUserByUserLogin(@RequestParam(name = "login") @NotBlank String login) {
        logger.info("getUserByUserLogin endpoint used with login value: " + login);
        User user = service.findUserByLogin(login);
        return ResponseEntity.ok(mapper.mapUserToUserDtoForLogin(user));
    }
    @PostMapping
    ResponseEntity<UserDTO> createUser(@RequestBody @Valid UserDTO userDTO) {
        User toSave = mapper.mapUserDtoToUser(userDTO);
        UserDTO result = mapper.mapUserToCreatedUserDto(service.createUser(toSave));
        return  ResponseEntity.created(URI.create("/medical/users?login=" + result.getLogin())).body(result);
    }

    @PutMapping
    ResponseEntity<Void> updateUser(@RequestBody @Valid UserDTO userDTO) {
        User user = mapper.mapUserDtoToUser(userDTO);
        service.updateUser(user);
        return ResponseEntity.noContent().build();
    }
}
