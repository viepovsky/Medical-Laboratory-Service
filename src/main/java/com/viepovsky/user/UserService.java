package com.viepovsky.user;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class UserService {
    private final UserRepository userRepository;

    User findUserByLogin(String login) {
        return userRepository.findUserByLogin(login).orElseThrow(() -> new EntityNotFoundException("User with login: " + login + " does not exist in database."));
    }
}
