package com.viepovsky.diagnose.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiagnosticResultResponse {
    private Long id;

    private String type;

    private String status;

    private LocalDateTime registration;
}
