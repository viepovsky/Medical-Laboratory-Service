package com.viepovsky.user;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/medical/user")
@RequiredArgsConstructor
class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService service;
    private final UserMapper mapper;

    @GetMapping
    ResponseEntity<UserForLoginDTO> getUserByUserLogin(@RequestParam(name = "login") @NotBlank String login) {
        logger.info("getUserByUserLogin endpoint used with login value: " + login);
        User user = service.findUserByLogin(login);
        return ResponseEntity.ok(mapper.mapUserToUserForLoginDTO(user));
    }
}
