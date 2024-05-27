package com.micro.flow.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.stream.Stream;

import static com.micro.flow.DomainTestingUtil.getViolation;
import static com.micro.flow.DomainTestingUtil.testInvalidField;

@SpringBootTest
@ActiveProfiles("test")
public class CredentialTests {
    private Credential credential;

    @BeforeEach
    public void initCredential() {
        credential = new Credential("user2Id", "secretData", "credentialData");
    }

    @Test
    public void testValidUser() {
        Assertions.assertEquals(0, getViolation(credential).size());
    }

    @ParameterizedTest
    @MethodSource("getEmptyAndNullArgs")
    public void testInvalidUserId(String userId) {
        credential.setUserId(userId);
        testInvalidField(credential, userId,
                "User id is empty, why? We are already working on it!");
    }

    @ParameterizedTest
    @MethodSource("getEmptyAndNullArgs")
    public void testInvalidType(String type) {
        credential.setType(type);
        testInvalidField(credential, type,
                "Type is empty, why? We are already working on it!");
    }

    @ParameterizedTest
    @MethodSource("getEmptyAndNullArgs")
    public void testInvalidSecretData(String secretData) {
        credential.setSecretData(secretData);
        testInvalidField(credential, secretData,
                "Your data is empty. We are working on it!");
    }

    @ParameterizedTest
    @MethodSource("getEmptyAndNullArgs")
    public void testInvalidCredentialData(String credentialData) {
        credential.setCredentialData(credentialData);
        testInvalidField(credential, credentialData,
                "Your data is empty. We are working on it!");
    }

    private static Stream<String> getEmptyAndNullArgs() {
        return Stream.of("", null);
    }

}
