package com.micro.flow;

import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTestingUtil {

    public static <T, V> void testInvalidField(T testedClass, V invalidValue,
                                                       String expectedErrorMessage) {
        int invalidValuesInClass = 1;

        assertEquals(invalidValuesInClass, getViolation(testedClass).size());
        assertEquals(invalidValue, getViolation(testedClass).iterator().next().getInvalidValue());
        assertEquals(expectedErrorMessage, getViolation(testedClass)
                .iterator()
                .next()
                .getMessage());
    }

    public static <T> Set<ConstraintViolation<T>> getViolation(T testedClass) {
        return Validation.buildDefaultValidatorFactory()
                .getValidator()
                .validate(testedClass);
    }

    public static <T> String asJsonString(T value) throws Exception {
        return new ObjectMapper().writeValueAsString(value);
    }
}
