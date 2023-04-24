package com.viepovsky.security;


import com.viepovsky.user.dto.request.AuthenticationUserRequest;
import com.viepovsky.user.dto.request.RegisterUserRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/medical/auth")
@RequiredArgsConstructor
@Validated
class AuthenticationController {
    private final AuthenticationService service;
    @PostMapping("/register")
    ResponseEntity<AuthenticationResponse> register(@RequestBody @Valid RegisterUserRequest request) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    ResponseEntity<AuthenticationResponse> authenticate(@RequestBody @Valid AuthenticationUserRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }
}