package com.viepovsky.diagnostic;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
class DiagnosticResultService {
    private DiagnosticResultRepository repository;
    List<DiagnosticResult> getAllDiagnosticResults(String login) {
        return repository.getDiagnosticResultByUser_Login(login);
    }
}
