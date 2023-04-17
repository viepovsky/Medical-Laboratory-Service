package com.viepovsky.diagnostic;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class DiagnosticResultDTO {
    private Long id;

    @NotNull
    private DiagnosticType typeName;

    private LocalDateTime registrationDate;

    @NotNull
    private byte[] pdfResults;

    @NotBlank
    private String userLogin;
}
