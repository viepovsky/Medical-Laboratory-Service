package com.viepovsky.diagnostic;

import com.viepovsky.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
class DiagnosticResultService {
    private final DiagnosticResultRepository resultRepository;
    private final UserRepository userRepository;

    @Transactional //HACK: @Transactional must be here due to not throwing exceptions during fetching pdfs.
    List<DiagnosticResult> getAllDiagnosticResults(String login) {
        return resultRepository.getDiagnosticResultByUser_Login(login);
    }

    DiagnosticResult saveDiagnosticResult(DiagnosticResult result, String login) {
        var retrievedUser = userRepository.findUserByLogin(login).orElseThrow(() -> new EntityNotFoundException("User with login: " + login + " does not exist in database."));
        result.setUser(retrievedUser);
        retrievedUser.getResultsList().add(result);
        userRepository.save(retrievedUser);
        return resultRepository.save(result);
    }
}
