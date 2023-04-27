package com.viepovsky.diagnose;

import com.viepovsky.user.User;
import com.viepovsky.utilities.BaseEntityAudit;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "DIAGNOSTIC_RESULTS")
public class DiagnosticResult extends BaseEntityAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "results_seq")
    @SequenceGenerator(name = "results_seq", initialValue = 500, allocationSize = 1)
    private Long id;

    @Enumerated(EnumType.STRING)
    private DiagnosticType type;

    @Enumerated(EnumType.STRING)
    private DiagnosticStatus status;

    private LocalDateTime registration;

    @Lob
    private byte[] resultsPdf;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    DiagnosticResult(DiagnosticType type, DiagnosticStatus status, LocalDateTime registration, byte[] resultsPdf) {
        this.type = type;
        this.status = status;
        this.registration = registration;
        this.resultsPdf = resultsPdf;
    }

    void updateFrom(DiagnosticResult result) {
        type = Optional.ofNullable(result.getType()).orElse(type);
        status = Optional.ofNullable(result.getStatus()).orElse(status);
        registration = Optional.ofNullable(result.getRegistration()).orElse(registration);
        resultsPdf = Optional.ofNullable(result.getResultsPdf()).orElse(resultsPdf);
    }
}
