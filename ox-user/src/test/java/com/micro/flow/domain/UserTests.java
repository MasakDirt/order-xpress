package com.micro.flow.domain;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class UserTests {
    private User user;

    @BeforeEach
    public void setUpUser() {
        user = new User();
        user.setEmail("email@mail.co");
        user.setUsername("username");
        user.setPassword("password");
        user.setRole(User.Role.USER);
        user.setId(1L);
    }

    @Test
    public void testValidUser() {
        Assertions.assertEquals(0, getViolation(user).size());
    }

    @ParameterizedTest
    @MethodSource("argumentsForEmail")
    public void testInvalidEmail(String email) {
        user.setEmail(email);
        testInvalidField(user, email,
                "Must be a valid e-mail address!");
    }

    private static Stream<String> argumentsForEmail() {
        return Stream.of("", "email", "ema@il",
                "user@.", "user@mail", "user@mai,co", "user@mail.comomo", null);
    }

    @ParameterizedTest
    @MethodSource("emptyAndNullArguments")
    public void testInvalidUsername(String username) {
        user.setUsername(username);
        testInvalidField(user, username,
                "Fill in your name please!");
    }

    @ParameterizedTest
    @MethodSource("emptyAndNullArguments")
    public void testInvalidPassword(String password) {
        user.setPassword(password);
        testInvalidField(user, password,
                "Password must be included!");
    }

    private static Stream<String> emptyAndNullArguments() {
        return Stream.of("", null);
    }

    @Test
    public void testNullRole() {
        user.setRole(null);

        testInvalidField(user, null,
                "Your role is null, sorry it's our mistake we are already working on it!");
    }

    private <T> void testInvalidField(User user, T invalidField,
                                               String expectedErrorMessage) {
        int invalidFieldsInClass = 1;

        assertEquals(invalidFieldsInClass, getViolation(user).size());
        assertEquals(invalidField, getViolation(user).iterator().next().getInvalidValue());
        assertEquals(expectedErrorMessage, getViolation(user)
                .iterator()
                .next()
                .getMessage());
    }

    private Set<ConstraintViolation<User>> getViolation(User user) {
        return Validation.buildDefaultValidatorFactory()
                .getValidator()
                .validate(user);
    }

}
