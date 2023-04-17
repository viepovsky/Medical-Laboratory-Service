package com.viepovsky.diagnostic;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
class DiagnosticResultMapper {
    DiagnosticResultDTO mapDiagnosticResultToDiagnosticResultDto(DiagnosticResult result) {
        return new DiagnosticResultDTO(
                result.getId(),
                result.getTypeName(),
                result.getRegistrationDate(),
                result.getPdfResults(),
                result.getUser().getLogin()
        );
    }

    List<DiagnosticResultDTO> mapDiagnosticResultListToDiagnosticResultDtoList(List<DiagnosticResult> results) {
        return results.stream()
                .map(this::mapDiagnosticResultToDiagnosticResultDto)
                .toList();
    }
}
