package com.viepovsky.security;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
class AuthenticationResponse {
    private String token;
}
