package com.micro.flow;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.micro.flow.domain.Bag;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BagTestingUtil {

    public static <V> void testInvalidField(Bag testedClass, V invalidValue,
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
