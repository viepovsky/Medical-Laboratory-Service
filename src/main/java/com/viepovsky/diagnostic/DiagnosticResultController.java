package com.viepovsky.diagnostic;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
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
    ResponseEntity<List<DiagnosticResultDTO>> getAllDiagnosticResults(@RequestParam(name = "login") @NotBlank String login) {
        logger.info("getAllDIagnosticResults endpoint used with login value: " + login);
        List<DiagnosticResult> results = service.getAllDiagnosticResults(login);
        return ResponseEntity.ok(mapper.mapToDiagnosticResultDtoList(results));
    }

    @PostMapping
    ResponseEntity<DiagnosticResultDTO> createDiagnosticResult(@RequestBody @Valid DiagnosticResultDTO resultDTO) {
        logger.info("createDiagnosticResult endpoint used with body: " + resultDTO.toString());
        DiagnosticResult toSave = mapper.mapToDiagnosticResult(resultDTO);
        DiagnosticResultDTO result = mapper.mapToDiagnosticResultDto(service.saveDiagnosticResult(toSave, resultDTO.getUserLogin()));
        return ResponseEntity.created(URI.create("/medical/results?login=" + result.getUserLogin())).body(result);
    }
}
