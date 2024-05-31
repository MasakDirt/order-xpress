package com.micro.flow.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static com.micro.flow.BagTestingUtil.getViolation;
import static com.micro.flow.BagTestingUtil.testInvalidField;

@SpringBootTest
@ActiveProfiles("test")
public class BagTests {
    private Bag bag;

    @BeforeEach
    public void initBag() {
        bag = new Bag("testeduser");
        bag.setTotalPrice(BigDecimal.valueOf(12));
    }

    @Test
    public void testValidBag() {
        Assertions.assertEquals(0, getViolation(bag).size());
    }

    @ParameterizedTest
    @MethodSource("getInvalidUsernames")
    public void testInvalidUsername(String username) {
        bag.setUsername(username);

        testInvalidField(bag, username,
                "Username must be lowercase and can contain only letters," +
                        " numbers, underscores, and dots");
    }

    private static Stream<String> getInvalidUsernames() {
        return Stream.of("userName", "usErname", "12Username", "",  null);
    }

    @ParameterizedTest
    @MethodSource("getInvalidTotalPrice")
    public void testInvalidTotalPrice(BigDecimal totalPrice) {
        bag.setTotalPrice(totalPrice);

        if (totalPrice == null) {
            testInvalidField(bag, totalPrice,
                    "Total price cannot be null!");
        } else {
            testInvalidField(bag, totalPrice,
                    "Total price cannot be lower that 0!");
        }
    }

    private static Stream<BigDecimal> getInvalidTotalPrice() {
        return Stream.of(BigDecimal.valueOf(-0.1), BigDecimal.valueOf(-1),
                BigDecimal.valueOf(-10), null);
    }
}
