package com.viepovsky.diagnose;

import com.viepovsky.diagnose.dto.DiagnosticResultRequest;
import com.viepovsky.diagnose.dto.DiagnosticResultResponse;
import io.github.viepovsky.polishutils.pesel.InvalidPeselException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
class DiagnosticResultFacade {
    private static final Logger LOGGER = LoggerFactory.getLogger(DiagnosticResultFacade.class);
    private final DiagnosticResultService diagnosticResultService;
    private final DiagnosticResultMapper mapper;

    List<DiagnosticResultResponse> getAllDiagnosticResults(String login) {
        LOGGER.info("Get all diagnostic results for user login:{}", login);
        List<DiagnosticResult> results = diagnosticResultService.getAllDiagnosticResultsByUserLogin(login);
        return mapper.mapToDiagnosticResultResponseList(results);
    }

    byte[] getDiagnosticResultPdf(Long id, String login) {
        LOGGER.info("Fetching pdf from diagnostic results of id:{} for user with login:{}", id, login);
        return diagnosticResultService.getDiagnosticResultPdfByIdAndUserLogin(id, login);
    }

    DiagnosticResultResponse createDiagnosticResult(DiagnosticResultRequest request) {
        LOGGER.info("Create diagnostic result for user login:{}", request.getUserLogin());
        var toSave = mapper.mapToDiagnosticResult(request);
        var savedResult = diagnosticResultService.saveDiagnosticResult(toSave, request.getUserLogin());
        return mapper.mapToDiagnosticResultResponse(savedResult);
    }

    void updateDiagnosticResult(DiagnosticResultRequest request, Long resultId) {
        LOGGER.info("Update diagnostic result with if of:{}", resultId);
        var toUpdate = mapper.mapToDiagnosticResult(request);
        diagnosticResultService.updateDiagnosticResult(toUpdate, request.getUserLogin(), resultId);
    }
}
