package com.viepovsky.diagnostic;

import com.viepovsky.user.User;
import com.viepovsky.utilities.BaseEntityAudit;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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

    private DiagnosticType type;

    private LocalDateTime registration;

    @Lob
    private byte[] resultsPdf;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public DiagnosticResult(DiagnosticType type, byte[] resultsPdf) {
        this.type = type;
        this.resultsPdf = resultsPdf;
    }
}
