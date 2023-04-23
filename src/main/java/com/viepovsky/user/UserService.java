package com.viepovsky.user;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class UserService {
    private final UserRepository repository;

    User getUserByLogin(String login) {
        return repository.findByLogin(login).orElseThrow(() -> new EntityNotFoundException("User with login: " + login + " does not exist in database."));
    }

    User createUser(User user) {
        if (repository.existsByLogin(user.getLogin())) {
            throw new EntityExistsException("User with login: " + user.getLogin() + " already exists in database.");
        }
        return repository.save(user);
    }

    void updateUser(User user) {
        var retrievedUser = repository.findByLogin(user.getLogin()).orElseThrow(() -> new EntityNotFoundException("User with login: " + user.getLogin() + " does not exist in database."));
        retrievedUser.updateFrom(user);
        repository.save(retrievedUser);
    }
}
