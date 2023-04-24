package com.viepovsky.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {
    @NotBlank(message = "Login must not be empty")
    private String login;

    @NotBlank(message = "Personal Id must not be empty")
    private String personalId;

    private String password;

    @Email(message = "Email is not valid")
    private String email;

    @NotBlank(message = "First name must not be empty")
    private String firstName;

    @NotBlank(message = "Last name must not be empty")
    private String lastName;

    private String phoneNumber;
}
