package com.viepovsky.diagnose;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
interface DiagnosticResultRepository extends JpaRepository<DiagnosticResult, Long> {
    List<DiagnosticResult> getDiagnosticResultByUser_Login(String login);

    Optional<DiagnosticResult> getDiagnosticResultByIdAndUser_Login(Long id, String login);
}
