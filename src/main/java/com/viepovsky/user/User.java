package com.viepovsky.user;

import com.viepovsky.diagnostic.DiagnosticResult;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USERS")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq")
    @SequenceGenerator(name="users_seq", initialValue=500, allocationSize = 1)
    private Long id;

    private String login;

    @Column(name = "pesel")
    private String personalId;

    private String password;

    private String email;

    private String name;

    private String lastName;

    private String phoneNumber;

    private UserRole role;

    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;

    @OneToMany(
            targetEntity = DiagnosticResult.class,
            mappedBy = "user",
            fetch = FetchType.LAZY
    )
    private List<DiagnosticResult> resultsList = new ArrayList<>();

    public User(String login, String personalId, String password, String email, String name, String lastName, String phoneNumber) {
        this.login = login;
        this.personalId = personalId;
        this.password = password;
        this.email = email;
        this.name = name;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        role = UserRole.USER;
        createdOn = LocalDateTime.now();
    }
}
