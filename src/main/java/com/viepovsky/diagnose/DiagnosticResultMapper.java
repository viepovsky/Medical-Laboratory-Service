package com.viepovsky.diagnose;

import com.viepovsky.diagnose.dto.DiagnosticResultRequest;
import com.viepovsky.diagnose.dto.DiagnosticResultResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class DiagnosticResultMapper {
    DiagnosticResultResponse mapToDiagnosticResultResponse(DiagnosticResult result) {
        return new DiagnosticResultResponse(
                result.getId(),
                result.getType().toString(),
                result.getStatus().toString(),
                result.getRegistration(),
                result.getResultsPdf()
        );
    }

    List<DiagnosticResultResponse> mapToDiagnosticResultResponseList(List<DiagnosticResult> results) {
        return results.stream()
                .map(this::mapToDiagnosticResultResponse)
                .toList();
    }

    DiagnosticResult mapToDiagnosticResult(DiagnosticResultRequest request) {
        return new DiagnosticResult(
                request.getType(),
                request.getStatus(),
                request.getRegistration(),
                request.getResultsPdf()
        );
    }
}
