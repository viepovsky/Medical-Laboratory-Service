package com.viepovsky.user;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
class UserDTO {
    private Long id;

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

    private Role role;

    private LocalDateTime createdOn;

    public UserDTO(Long id, String login) {
        this.id = id;
        this.login = login;
    }

    public UserDTO(String login, String password, Role role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }
}
