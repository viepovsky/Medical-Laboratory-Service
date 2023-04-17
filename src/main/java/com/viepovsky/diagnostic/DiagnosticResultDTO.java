package com.viepovsky.diagnostic;

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

    private DiagnosticType typeName;

    private LocalDateTime registrationDate;

    private byte[] pdfResults;

    private String userLogin;
}
