package com.viepovsky.diagnostic;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    ResponseEntity<List<DiagnosticResultDTO>> getAllDiagnosticResults(@Param("login") @NotBlank String login) {
        List<DiagnosticResult> results = service.getAllDiagnosticResults(login);
        return ResponseEntity.ok(mapper.mapDiagnosticResultListToDiagnosticResultDtoList(results));
    }
}
