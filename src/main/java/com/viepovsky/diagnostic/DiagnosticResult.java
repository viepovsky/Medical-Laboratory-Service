package com.viepovsky.diagnostic;

import com.viepovsky.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
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
    private byte[] pdfResults;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public DiagnosticResult(DiagnosticType typeName, byte[] pdfResults) {
        this.typeName = typeName;
        this.pdfResults = pdfResults;
        registrationDate = LocalDateTime.now();
    }
}
