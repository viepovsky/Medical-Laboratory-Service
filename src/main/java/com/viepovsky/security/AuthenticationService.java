package com.viepovsky.security;

import com.viepovsky.security.dto.AuthenticationResponse;
import com.viepovsky.user.User;
import com.viepovsky.user.UserService;
import com.viepovsky.user.dto.request.AuthenticationUserRequest;
import com.viepovsky.user.dto.request.RegisterUserRequest;
import io.github.viepovsky.polishutils.pesel.InvalidPeselException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class AuthenticationService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    AuthenticationResponse register(RegisterUserRequest request) throws InvalidPeselException {
        var user = User.builder()
                .login(request.getLogin())
                .personalId(request.getPersonalId())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .name(request.getFirstName())
                .lastName(request.getLastName())
                .phoneNumber(request.getPhoneNumber())
                .build();
        var createdUser = userService.createUser(user);
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
        var user = userService.getUserByLogin(request.getLogin());
        var jwtToken = jwtService.generateJwtToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
