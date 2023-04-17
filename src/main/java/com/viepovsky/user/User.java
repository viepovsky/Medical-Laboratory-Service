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
    @GeneratedValue
    private Long id;

    private String login;

    @Column(name = "pesel")
    private String personalId;

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
        createdDate = LocalDateTime.now();
    }
}
