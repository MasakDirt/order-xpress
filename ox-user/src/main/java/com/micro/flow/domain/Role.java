package com.micro.flow.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "keycloak_role")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "name")
@ToString(of = "name")
public class Role implements GrantedAuthority {

    @Id
    private String id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(name = "realm_id")
    private String realmId;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();

    public Role(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return "ROLE_" + name;
    }

}
