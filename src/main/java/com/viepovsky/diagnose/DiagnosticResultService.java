package com.viepovsky.diagnose;

import com.viepovsky.user.UserService;
import io.github.viepovsky.polishutils.pesel.InvalidPeselException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
class DiagnosticResultService {
    private final DiagnosticResultRepository diagnosticResultRepository;
    private final UserService userService;

    List<DiagnosticResult> getAllDiagnosticResultsByUserLogin(String login) {
        return diagnosticResultRepository.getDiagnosticResultByUser_Login(login);
    }

    byte[] getDiagnosticResultPdfByIdAndUserLogin(Long id, String login) {
        var diagnosticResults = diagnosticResultRepository.getDiagnosticResultByIdAndUser_Login(id, login)
                .orElseThrow(() -> new EntityNotFoundException("DiagnosticResult with id: " + id + " for user login: "
                        + login + " does not exist in database."));
        return diagnosticResults.getResultsPdf();
    }

    DiagnosticResult saveDiagnosticResult(DiagnosticResult result, String login) throws InvalidPeselException {
        var retrievedUser = userService.getUserByLogin(login);
        result.setUser(retrievedUser);
        retrievedUser.getResultsList().add(result);
        userService.updateUser(retrievedUser);
        return diagnosticResultRepository.save(result);
    }

    void updateDiagnosticResult(DiagnosticResult result, String login, Long resultId) {
        var resultToUpdate = diagnosticResultRepository.findById(resultId)
                .orElseThrow(() -> new EntityNotFoundException("DiagnosticResult with id: "
                        + resultId + " does not exist in database."));
        var user = userService.getUserByLogin(login);
        resultToUpdate.updateFrom(result);
        resultToUpdate.setUser(user);
        diagnosticResultRepository.save(resultToUpdate);
    }
}
