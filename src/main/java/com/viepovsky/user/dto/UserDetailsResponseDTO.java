package com.viepovsky.user.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsResponseDTO {
    private Long id;

    private String login;

    private String personalId;

    private String email;

    private String firstName;

    private String lastName;

    private String phoneNumber;
}
