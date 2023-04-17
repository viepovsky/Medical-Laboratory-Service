package com.viepovsky.diagnostic;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DiagnosticResultDTO {
    private Long id;

    @NotNull
    private DiagnosticType type;

    private LocalDateTime registration;

    @NotNull
    private byte[] resultsPdf;

    @NotBlank
    private String userLogin;
}
