package com.viepovsky.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
enum UserRole {
    USER("USER"),
    ADMIN("ADMIN");
    private final String userRole;
}
