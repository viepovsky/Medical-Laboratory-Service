package com.viepovsky.diagnose;

import com.viepovsky.diagnose.dto.DiagnosticResultRequest;
import com.viepovsky.diagnose.dto.DiagnosticResultResponse;
import io.github.viepovsky.polishutils.pesel.InvalidPeselException;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.List;

@Component
@RequiredArgsConstructor
class DiagnosticResultFacade {
    private static final Logger logger = LoggerFactory.getLogger(DiagnosticResultFacade.class);
    private final DiagnosticResultService service;
    private final DiagnosticResultMapper mapper;
    private final LoginValidator validator;

    ResponseEntity<List<DiagnosticResultResponse>> getAllDiagnosticResults(String login) {
        logger.info("getAllDIagnosticResults endpoint used with login value: " + login);
        if (!validator.isUserAuthorized(login)) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        List<DiagnosticResult> results = service.getAllDiagnosticResults(login);
        return ResponseEntity.ok(mapper.mapToDiagnosticResultResponseList(results));
    }

    ResponseEntity<DiagnosticResultResponse> createDiagnosticResult(DiagnosticResultRequest request) throws InvalidPeselException {
        logger.info("createDiagnosticResult endpoint used");
        DiagnosticResult toSave = mapper.mapToDiagnosticResult(request);
        DiagnosticResultResponse result = mapper.mapToDiagnosticResultResponse(service.saveDiagnosticResult(toSave, request.getUserLogin()));
        return ResponseEntity.created(URI.create("/medical/results?login=" + request.getUserLogin())).body(result);
    }

    ResponseEntity<Void> updateDiagnosticResult(DiagnosticResultRequest request, Long resultId) {
        logger.info("updateDiagnosticResult endpoint used");
        DiagnosticResult toUpdate = mapper.mapToDiagnosticResult(request);
        service.updateDiagnosticResult(toUpdate, request.getUserLogin(), resultId);
        return ResponseEntity.noContent().build();
    }
}
