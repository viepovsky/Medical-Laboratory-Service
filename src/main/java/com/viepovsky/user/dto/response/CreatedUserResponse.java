package com.viepovsky.user.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatedUserResponse {
    private Long id;
    private String login;
}
