package com.micro.flow.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "user_entity")
@EqualsAndHashCode(of = "username")
@ToString(of = "username")
public class User {

    @Id
    private String id;

    @NaturalId
    @Column(nullable = false, updatable = false, unique = true)
    @NotEmpty(message = "Fill in your name please!")
    @Pattern(regexp = "^[a-z0-9_.]+$", message = "Username must be lowercase" +
            " and can contain only letters, numbers, underscores, and dots")
    private String username;

    @NotNull(message = "Must be a valid e-mail address!")
    @Pattern(regexp = "[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}",
            message = "Must be a valid e-mail address!")
    @Column(nullable = false, updatable = false, unique = true)
    private String email;

    @Column(name = "email_constraint")
    private String emailConstraint;

    @Column(name = "email_verified")
    private boolean isEmailVerified;

    @Column(name = "enabled")
    private boolean isEnabled;

    @Column(name = "realm_id")
    private String realmId;

    @Column(name = "created_timestamp")
    private long createdTimestamp;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role_mapping",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();

    public void setImportantValues(String realmId, Set<Role> roles) {
        this.id = UUID.randomUUID().toString();
        this.createdTimestamp = Instant.now().toEpochMilli();

        this.realmId = realmId;
        this.isEmailVerified = true;
        this.isEnabled = true;
        this.emailConstraint = this.email;
        this.roles = roles;
    }
}
