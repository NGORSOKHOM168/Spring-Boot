package com.backend.entity;

import java.util.List;

import com.backend.model.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(
    name = "user_app",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
    })
public class User {
    @Id
    @SequenceGenerator(
        name = "user_generator",
        sequenceName = "user_app_seq",
        allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
    @Column(name = "id")
    private Long id;

    @Column(length = 30)
    private String firstName;

    @Column(length = 30)
    private String lastName;

    @Column(length = 100)
    private String email;

    @JsonIgnore // to prevent the password from being returned in the response body.
    private String password;

    @JsonIgnore
    @Transient
    private String confirmPassword;

    @Enumerated(EnumType.STRING)
    private Role role;

    /**
     * the user by default is not enable, until he activates his account.
     */
    private boolean enabled; // by default is false, until the user activates his account via email verification.
    
    private boolean accountNonLocked; // by default is true, until the user is blocked by the admin.

    private int failedAttempts;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Token> tokens;

    public static User of(String firstName, String lastName, String email, String password, String confirmPassword, Role role) {
        return new User(null, firstName, lastName, email, password, confirmPassword, role, false, true, 0, null);
    }
   
}