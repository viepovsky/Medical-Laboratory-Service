package com.viepovsky.diagnostic;

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
    private DiagnosticType typeName;

    private LocalDateTime registrationDate;

    @NotNull
    private byte[] resultsPdf;

    @NotBlank
    private String userLogin;
}
