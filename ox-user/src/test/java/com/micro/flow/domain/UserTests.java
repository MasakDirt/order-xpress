package com.micro.flow.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Set;
import java.util.stream.Stream;

import static com.micro.flow.DomainTestingUtil.getViolation;
import static com.micro.flow.DomainTestingUtil.testInvalidField;

@SpringBootTest
@ActiveProfiles("test")
public class UserTests {
    private User user;

    @BeforeEach
    public void setUpUser() {
        user = new User();
        user.setEmail("email@mail.co");
        user.setUsername("username");
        user.setEmailConstraint("email@mail.co");
        user.setRealmId("Realm id");
        user.setEmailVerified(true);
        user.setRoles(Set.of(new Role("ROLE_USER")));
        user.setId("1L");
    }

    @Test
    public void testValidUser() {
        Assertions.assertEquals(0, getViolation(user).size());
    }

    @ParameterizedTest
    @MethodSource("invalidArgumentsForEmail")
    public void testInvalidEmail(String email) {
        user.setEmail(email);
        testInvalidField(user, email,
                "Must be a valid e-mail address!");
    }

    private static Stream<String> invalidArgumentsForEmail() {
        return Stream.of("", "email", "ema@il",
                "user@.", "user@mail", "user@mai,co", "user@mail.comomo", null);
    }

    @ParameterizedTest
    @MethodSource("invalidArgumentsForUsername")
    public void testInvalidUsername(String username) {
        user.setUsername(username);
        testInvalidField(user, username,
                "Username must be lowercase and can contain only letters,"
                        + " numbers, underscores, and dots");
    }

    private static Stream<String> invalidArgumentsForUsername() {
        return Stream.of("Username", "userName", "usernamE",  "", null);
    }

}
