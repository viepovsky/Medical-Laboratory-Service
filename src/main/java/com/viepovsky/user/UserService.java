package com.viepovsky.user;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class UserService {
    private final UserRepository repository;

    User findUserByLogin(String login) {
        return repository.findUserByLogin(login).orElseThrow(() -> new EntityNotFoundException("User with login: " + login + " does not exist in database."));
    }

    void createUser(User user) {
        repository.save(user);
    }

    void updateUser(User user) {
        if (repository.existsByLogin(user.getLogin())) {
            var retrievedUser = repository.findUserByLogin(user.getLogin()).get();
            user.setId(retrievedUser.getId());
            repository.save(user);
        } else {
            throw new EntityNotFoundException("User with login: " + user.getLogin() + " does not exist in database.");
        }
    }
}
