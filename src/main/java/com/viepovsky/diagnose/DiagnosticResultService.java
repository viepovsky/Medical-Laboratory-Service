package com.viepovsky.diagnose;

import com.viepovsky.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
class DiagnosticResultService {
    private final DiagnosticResultRepository repository;
    private final UserService service;

    @Transactional //HACK: @Transactional must be here due to not throwing exceptions during fetching pdfs.
    List<DiagnosticResult> getAllDiagnosticResults(String login) {
        return repository.getDiagnosticResultByUser_Login(login);
    }

    DiagnosticResult saveDiagnosticResult(DiagnosticResult result, String login) {
        var retrievedUser = service.getUserByLogin(login);
        result.setUser(retrievedUser);
        retrievedUser.getResultsList().add(result);
        service.updateUser(retrievedUser);
        return repository.save(result);
    }

    void updateDiagnosticResult(DiagnosticResult result, String login, Long resultId) {
        var resultToUpdate = repository.findById(resultId).orElseThrow(() -> new EntityNotFoundException("DiagnosticResult with id: " + resultId + " does not exist in database."));
        var user = service.getUserByLogin(login);
        resultToUpdate.updateFrom(result);
        resultToUpdate.setUser(user);
        repository.save(resultToUpdate);
    }
}
