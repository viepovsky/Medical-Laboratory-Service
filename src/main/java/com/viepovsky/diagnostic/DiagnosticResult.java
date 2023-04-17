package com.viepovsky.diagnostic;

import com.viepovsky.user.User;
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
public class DiagnosticResult {

    @Id
    @GeneratedValue
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
        registration = LocalDateTime.now();
    }
}
