package com.viepovsky.user.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetailsUserResponse {
    private Long id;

    private String login;

    private String personalId;

    private String email;

    private String firstName;

    private String lastName;

    private String phoneNumber;
}
