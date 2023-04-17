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

    private DiagnosticType typeName;

    @Column(name = "registration_date")
    private LocalDateTime registrationDate;

    @Lob
    private byte[] resultsPdf;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public DiagnosticResult(DiagnosticType typeName, byte[] resultsPdf) {
        this.typeName = typeName;
        this.resultsPdf = resultsPdf;
        registrationDate = LocalDateTime.now();
    }
}
