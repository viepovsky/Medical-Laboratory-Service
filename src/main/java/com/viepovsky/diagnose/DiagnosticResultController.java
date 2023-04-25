package com.viepovsky.diagnose;

import com.viepovsky.diagnose.dto.DiagnosticResultRequest;
import com.viepovsky.diagnose.dto.DiagnosticResultResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/medical/results")
@Validated
class DiagnosticResultController {
    private static final Logger logger = LoggerFactory.getLogger(DiagnosticResultController.class);
    private final DiagnosticResultService service;
    private final DiagnosticResultMapper mapper;

    @GetMapping
    ResponseEntity<List<DiagnosticResultResponse>> getAllDiagnosticResults(@RequestParam(name = "login") @NotBlank String login) {
        logger.info("getAllDIagnosticResults endpoint used with login value: " + login);
        String loginFromToken = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!loginFromToken.equals(login)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        List<DiagnosticResult> results = service.getAllDiagnosticResults(login);
        return ResponseEntity.ok(mapper.mapToDiagnosticResultResponseList(results));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    ResponseEntity<DiagnosticResultResponse> createDiagnosticResult(@RequestBody @Valid DiagnosticResultRequest request) {
        logger.info("createDiagnosticResult endpoint used");
        DiagnosticResult toSave = mapper.mapToDiagnosticResult(request);
        DiagnosticResultResponse result = mapper.mapToDiagnosticResultResponse(service.saveDiagnosticResult(toSave, request.getUserLogin()));
        return ResponseEntity.created(URI.create("/medical/results?login=" + request.getUserLogin())).body(result);
    }
}
