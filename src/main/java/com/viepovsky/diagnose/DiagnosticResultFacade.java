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
    private static final Logger logger = LoggerFactory.getLogger(DiagnosticResultFacade.class);
    private final DiagnosticResultService service;
    private final DiagnosticResultMapper mapper;

    List<DiagnosticResultResponse> getAllDiagnosticResults(String login) {
        logger.info("getAllDIagnosticResults endpoint used with login value: " + login);
        List<DiagnosticResult> results = service.getAllDiagnosticResults(login);
        return mapper.mapToDiagnosticResultResponseList(results);
    }

    DiagnosticResultResponse createDiagnosticResult(DiagnosticResultRequest request) throws InvalidPeselException {
        logger.info("createDiagnosticResult endpoint used");
        var toSave = mapper.mapToDiagnosticResult(request);
        var savedResult = service.saveDiagnosticResult(toSave, request.getUserLogin());
        return mapper.mapToDiagnosticResultResponse(savedResult);
    }

    void updateDiagnosticResult(DiagnosticResultRequest request, Long resultId) {
        logger.info("updateDiagnosticResult endpoint used");
        var toUpdate = mapper.mapToDiagnosticResult(request);
        service.updateDiagnosticResult(toUpdate, request.getUserLogin(), resultId);
    }
}
