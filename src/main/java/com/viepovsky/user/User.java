package com.viepovsky.user;

import com.viepovsky.diagnostic.DiagnosticResult;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USERS")
public class User {
    @Id
    @GeneratedValue
    private Long id;

    private String login;

    @NotBlank
    @Column(name = "pesel")
    private String personalId;

    @Pattern(regexp = "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\W])(?=\\S+$).{8,}", message = "Password should contain at least 8 characters, one uppercase letter, one lowercase letter, and one special character.")
    private String password;

    private String email;

    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone_number")
    private String phoneNumber;

    private UserRole role;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @OneToMany(
            targetEntity = DiagnosticResult.class,
            mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<DiagnosticResult> resultsList = new ArrayList<>();
}
