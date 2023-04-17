package com.viepovsky.diagnostic;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
class DiagnosticResultMapper {
    DiagnosticResultDTO mapToDiagnosticResultDto(DiagnosticResult result) {
        return new DiagnosticResultDTO(
                result.getId(),
                result.getTypeName(),
                result.getRegistrationDate(),
                result.getResultsPdf(),
                result.getUser().getLogin()
        );
    }

    List<DiagnosticResultDTO> mapToDiagnosticResultDtoList(List<DiagnosticResult> results) {
        return results.stream()
                .map(this::mapToDiagnosticResultDto)
                .toList();
    }

    DiagnosticResult mapToDiagnosticResult(DiagnosticResultDTO resultDTO) {
        return new DiagnosticResult(
                resultDTO.getTypeName(),
                resultDTO.getResultsPdf()
        );
    }
}
