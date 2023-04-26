package com.viepovsky.diagnose;

import com.viepovsky.diagnose.dto.DiagnosticResultRequest;
import com.viepovsky.diagnose.dto.DiagnosticResultResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/medical/results")
@Validated
class DiagnosticResultController {
    private final DiagnosticResultFacade facade;

    @GetMapping
    ResponseEntity<List<DiagnosticResultResponse>> getAllDiagnosticResults(@RequestParam(name = "login") @NotBlank String login) {
        return facade.getAllDiagnosticResults(login);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    ResponseEntity<DiagnosticResultResponse> createDiagnosticResult(@RequestBody @Valid DiagnosticResultRequest request) {
        return facade.createDiagnosticResult(request);
    }
}
