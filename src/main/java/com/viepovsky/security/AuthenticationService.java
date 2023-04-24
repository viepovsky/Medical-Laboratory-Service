package com.viepovsky.security;

import com.viepovsky.user.User;
import com.viepovsky.user.UserService;
import com.viepovsky.user.dto.AuthenticationUserRequest;
import com.viepovsky.user.dto.RegisterUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class AuthenticationService {
    private final UserService service;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    AuthenticationResponse register(RegisterUserRequest request) {
        var user = User.builder()
                .login(request.getLogin())
                .personalId(request.getPersonalId())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .name(request.getFirstName())
                .lastName(request.getLastName())
                .phoneNumber(request.getPhoneNumber())
                .build();
        var createdUser = service.createUser(user);
        var jwtToken = jwtService.generateJwtToken(createdUser);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    AuthenticationResponse authenticate(AuthenticationUserRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getLogin(),
                        request.getPassword()
                )
        );
        var user = service.getUserByLogin(request.getLogin());
        var jwtToken = jwtService.generateJwtToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
