package com.viepovsky.user;

import com.viepovsky.exceptions.PasswordValidationException;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public User getUserByLogin(String login) {
        return repository.findByLogin(login).orElseThrow(() -> new EntityNotFoundException("User with login: " + login + " does not exist in database."));
    }

    public User createUser(User user) {
        if (repository.existsByLogin(user.getLogin())) {
            throw new EntityExistsException("User with login: " + user.getLogin() + " already exists in database.");
        }
        return repository.save(user);
    }

    public void updateUser(User user) {
        var retrievedUser = repository.findByLogin(user.getLogin()).orElseThrow(() -> new EntityNotFoundException("User with login: " + user.getLogin() + " does not exist in database."));
        retrievedUser.updateFrom(user);
        repository.save(retrievedUser);
    }

    void updateUserWithPassword(User user) throws PasswordValidationException {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (user.getPassword().matches("(?=.*[a-z])(?=.*[A-Z])(?=.*[\\W])(?=\\S+$).{8,}")) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            updateUser(user);
        } else {
            throw new PasswordValidationException("Password should contain at least 8 characters, one uppercase letter, one lowercase letter, and one special character.");
        }
    }
}
