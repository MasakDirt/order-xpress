package com.micro.flow.oauth2component;

import com.micro.flow.oauth2config.JwtConverterProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Component
@AllArgsConstructor
public class JwtConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    private static final String PREFERRED_JWT_USERNAME = "upn";
    private static final JwtGrantedAuthoritiesConverter JWT_GRANTED_AUTHORITIES_CONVERTER
            = new JwtGrantedAuthoritiesConverter();
    private final JwtConverterProperties properties;

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        Collection<GrantedAuthority> authorities = Stream.concat(
                        JWT_GRANTED_AUTHORITIES_CONVERTER.convert(jwt).stream(),
                        extractRolesFromJwt(jwt).stream())
                .collect(Collectors.toSet());
        String principalClaim = getPrincipalClaimName(jwt);
        log.debug("Extracted principal claim: {}", principalClaim);
        log.debug("Jwt claims: {}", jwt.getClaims());
        return new JwtAuthenticationToken(jwt, authorities, principalClaim);
    }

    private Collection<? extends GrantedAuthority> extractRolesFromJwt(Jwt jwt) {
        return Stream.concat(
                        extractRealmRoles(jwt).stream(),
                        extractResourceRoles(jwt).stream())
                .collect(Collectors.toSet());
    }

    private Collection<? extends GrantedAuthority> extractRealmRoles(Jwt jwt) {
        Map<String, Object> realmAccess = jwt.getClaim("realm_access");
        if (realmAccess != null) {
            Collection<String> realmRoles = (Collection<String>) realmAccess.get("roles");
            if (realmRoles != null) {
                return realmRoles.stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                        .collect(Collectors.toSet());
            }
        }
        return Set.of();
    }

    private Collection<? extends GrantedAuthority> extractResourceRoles(Jwt jwt) {
        Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
        if (resourceAccess != null) {
            Map<String, Object> resource = (Map<String, Object>) resourceAccess.get(properties.getResourceId());
            if (resource != null) {
                Collection<String> resourceRoles = (Collection<String>) resource.get("roles");
                if (resourceRoles != null) {
                    return resourceRoles.stream()
                            .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                            .collect(Collectors.toSet());
                }
            }
        }
        return Set.of();
    }

    private String getPrincipalClaimName(Jwt jwt) {
        String claimName = properties.getPrincipalAttribute() != null
                ? properties.getPrincipalAttribute() : PREFERRED_JWT_USERNAME;
        return jwt.getClaim(claimName);
    }
}
