package com.viepovsky.diagnose;

import com.viepovsky.diagnose.dto.DiagnosticResultRequest;
import com.viepovsky.diagnose.dto.DiagnosticResultResponse;
import com.viepovsky.utilities.LoginValidator;
import io.github.viepovsky.polishutils.pesel.InvalidPeselException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/medical/results")
@Validated
class DiagnosticResultController {
    private final DiagnosticResultFacade diagnosticResultFacade;
    private final LoginValidator loginValidator;

    @GetMapping
    ResponseEntity<List<DiagnosticResultResponse>> getAllDiagnosticResultsDetails(@RequestParam(name = "login") @NotBlank String login) {
        if (!loginValidator.isUserAuthorized(login)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(diagnosticResultFacade.getAllDiagnosticResults(login));
    }

    @GetMapping(
            value = "{id}",
            produces = MediaType.APPLICATION_PDF_VALUE)
    ResponseEntity<byte[]> getDiagnosticResultPdf(
            @PathVariable(name = "id") @Min(1) Long id,
            @RequestParam(name = "login") @NotBlank String login) {
        if (!loginValidator.isUserAuthorized(login)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(diagnosticResultFacade.getDiagnosticResultPdf(id, login));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    ResponseEntity<DiagnosticResultResponse> createDiagnosticResult(@RequestBody @Valid DiagnosticResultRequest request) {
        var result = diagnosticResultFacade.createDiagnosticResult(request);
        return ResponseEntity.created(URI.create("/medical/results?login=" + request.getUserLogin())).body(result);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(path = "/{id}")
    ResponseEntity<Void> updateDiagnosticResult(
            @RequestBody @Valid DiagnosticResultRequest request,
            @PathVariable @Min(1) Long id) {
        diagnosticResultFacade.updateDiagnosticResult(request, id);
        return ResponseEntity.noContent().build();
    }
}
