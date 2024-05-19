package com.micro.flow.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "credential")
@ToString(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class Credential {
    private static final String PASSWORD_CREDENTIAL_TYPE = "password";
    private static final int DEFAULT_PASSWORD_PRIORITY = 10;

    @Id
    private String id;

    @Column(name = "user_id")
    @NotEmpty(message = "User id is empty, why? We are already working on it!")
    private String userId;

    @NotEmpty(message = "Type is empty, why? We are already working on it!")
    private String type;

    private long createdDate;

    @NotEmpty(message = "Your data is empty. We are looking for it!")
    private String secretData;

    @NotEmpty(message = "Your data is empty. We are looking for it!")
    private String credentialData;

    private int priority;

    public Credential(String userId, String secretData, String credentialData) {
        this.id = UUID.randomUUID().toString();
        this.createdDate = Instant.now().toEpochMilli();
        this.type = PASSWORD_CREDENTIAL_TYPE;
        this.priority = DEFAULT_PASSWORD_PRIORITY;

        this.userId = userId;
        this.secretData = secretData;
        this.credentialData = credentialData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Credential that = (Credential) o;
        return id != null && this.id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
