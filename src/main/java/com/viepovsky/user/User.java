package com.viepovsky.user;

import com.viepovsky.diagnostic.DiagnosticResult;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
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
// TODO: This password should be already encrypted so Validation shouldn't be here, I will change it later
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

    public User(String login, String personalId, String password, String email, String name, String lastName, String phoneNumber) {
        if (login.isEmpty()) {
            this.login = personalId;
        } else {
            this.login = login;
        }
        this.personalId = personalId;
        this.password = password;
        this.email = email;
        this.name = name;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.role = UserRole.USER;
        this.createdDate = LocalDateTime.now();
    }
}
