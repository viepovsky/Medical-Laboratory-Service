package com.viepovsky.user;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class UserDTO {
    @NotBlank(message = "Login must not be empty")
    private String login;

    @NotBlank(message = "Personal Id must not be empty")
    private String personalId;

    @Pattern(regexp = "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\W])(?=\\S+$).{8,}", message = "Password should contain at least 8 characters, one uppercase letter, one lowercase letter, and one special character.")
    private String password;

    @Email(message = "Email is not valid")
    private String email;

    @NotBlank(message = "Name must not be empty")
    private String name;

    @NotBlank(message = "Last name must not be empty")
    private String lastName;

    private String phoneNumber;
}
