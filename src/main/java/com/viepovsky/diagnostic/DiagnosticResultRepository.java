package com.viepovsky.diagnostic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface DiagnosticResultRepository extends JpaRepository<DiagnosticResult, Long> {
    List<DiagnosticResult> getDiagnosticResultByUser_Login(String login);
}
