package com.viepovsky.user;

import com.viepovsky.diagnose.DiagnosticResult;
import com.viepovsky.utilities.BaseEntityAudit;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USERS")
public class User extends BaseEntityAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq")
    @SequenceGenerator(name = "users_seq", initialValue = 500, allocationSize = 1)
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
    }

    void updateFrom(User user) {
        login = Optional.ofNullable(user.getLogin()).orElse(login);
        personalId = Optional.ofNullable(user.getPersonalId()).orElse(personalId);
        password = Optional.ofNullable(user.getPassword()).orElse(password);
        email = Optional.ofNullable(user.getEmail()).orElse(email);
        name = Optional.ofNullable(user.getName()).orElse(name);
        lastName = Optional.ofNullable(user.getLastName()).orElse(lastName);
        phoneNumber = Optional.ofNullable(user.getPhoneNumber()).orElse(phoneNumber);
    }

    @Override
    public void prePersist() {
        if (role == null) {
            role = UserRole.USER;
        }
        super.prePersist();
    }
}
