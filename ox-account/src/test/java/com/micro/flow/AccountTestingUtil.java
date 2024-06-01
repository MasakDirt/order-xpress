package com.micro.flow;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.micro.flow.domain.Account;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountTestingUtil {

    public static <V> void testInvalidField(Account testedClass, V invalidValue,
                                            String expectedErrorMessage) {
        int invalidValuesInClass = 1;

        assertEquals(invalidValuesInClass, getViolation(testedClass).size());
        assertEquals(invalidValue, getViolation(testedClass).iterator().next().getInvalidValue());
        assertEquals(expectedErrorMessage, getViolation(testedClass)
                .iterator()
                .next()
                .getMessage());
    }

    public static Set<ConstraintViolation<Account>> getViolation(Account testedClass) {
        return Validation.buildDefaultValidatorFactory()
                .getValidator()
                .validate(testedClass);
    }

    public static <T> String asJsonString(T value) throws Exception {
        return new ObjectMapper().writeValueAsString(value);
    }
}
