package com.viepovsky.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
class UserForLoginDTO {
    private String login;
    private String password;
    private UserRole role;
}
