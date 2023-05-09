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
    private final DiagnosticResultFacade facade;
    private final LoginValidator validator;

    @GetMapping
    ResponseEntity<List<DiagnosticResultResponse>> getAllDiagnosticResults(@RequestParam(name = "login") @NotBlank String login) {
        if (!validator.isUserAuthorized(login)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(facade.getAllDiagnosticResults(login));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    ResponseEntity<DiagnosticResultResponse> createDiagnosticResult(@RequestBody @Valid DiagnosticResultRequest request) throws InvalidPeselException {
        var result = facade.createDiagnosticResult(request);
        return ResponseEntity.created(URI.create("/medical/results?login=" + request.getUserLogin())).body(result);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(path = "/{id}")
    ResponseEntity<Void> updateDiagnosticResult(
            @RequestBody @Valid DiagnosticResultRequest request,
            @PathVariable @Min(1) Long id) {
        facade.updateDiagnosticResult(request, id);
        return ResponseEntity.noContent().build();
    }
}
