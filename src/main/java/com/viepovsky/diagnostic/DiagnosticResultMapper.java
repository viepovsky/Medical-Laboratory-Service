package com.viepovsky.diagnostic;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
class DiagnosticResultMapper {
    DiagnosticResultDTO mapToDiagnosticResultDto(DiagnosticResult result) {
        return new DiagnosticResultDTO(
                result.getId(),
                result.getType(),
                result.getRegistration(),
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
                resultDTO.getType(),
                resultDTO.getResultsPdf()
        );
    }
}
