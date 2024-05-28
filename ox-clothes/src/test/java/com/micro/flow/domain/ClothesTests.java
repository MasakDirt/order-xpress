package com.micro.flow.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import static com.micro.flow.ClothesTestingUtil.*;

@SpringBootTest
@ActiveProfiles("test")
public class ClothesTests {
    private Clothes clothes;

    @BeforeEach
    public void initClothes() {
        this.clothes = new Clothes();
        clothes.setId(1L);
        clothes.setPrice(BigDecimal.valueOf(120));
        clothes.setSex(Sex.MALE);
        clothes.setProductName("Socks");
        clothes.setDescription("Socks for males");
        clothes.setColors(List.of("red", "green", "blue"));
        clothes.setSizes(List.of(SampleSize.XS, SampleSize.L));
        clothes.setAvailableAmount(120);
        clothes.setType(Type.SOCKS);
        clothes.setSellerUsername("adminito25");
    }

    @Test
    public void testValidClothes() {
        Assertions.assertEquals(0, getViolation(clothes).size());
    }

    @ParameterizedTest
    @MethodSource("getEmptyAndNullArgs")
    public void testInvalidProductName(String productName) {
        clothes.setProductName(productName);

        testInvalidField(clothes, productName,
                "Product name must be included!");
    }

    @ParameterizedTest
    @MethodSource("getEmptyAndNullArgs")
    public void testInvalidDescription(String description) {
        clothes.setDescription(description);

        testInvalidField(clothes, description,
                "Describe this product!");
    }

    @ParameterizedTest
    @MethodSource("getEmptyAndNullArgs")
    public void testInvalidSellerUsername(String sellerUsername) {
        clothes.setSellerUsername(sellerUsername);

        testInvalidField(clothes, sellerUsername,
                "Seller username cannot be empty!");
    }

    private static Stream<String> getEmptyAndNullArgs() {
        return Stream.of("", null);
    }

    @ParameterizedTest
    @MethodSource("getInvalidPriceValues")
    public void testInvalidPrice(BigDecimal price) {
        clothes.setPrice(price);

        if (price == null) {
            testInvalidField(clothes, price,
                    "Price must be included!");
        } else {
            testInvalidField(clothes, price,
                    "Price cannot be lower that 0!");
        }
    }

    private static Stream<BigDecimal> getInvalidPriceValues() {
        return Stream.of(BigDecimal.valueOf(-0.1), BigDecimal.valueOf(-1),
                BigDecimal.valueOf(-10), null);
    }

    @Test
    public void testNullSex() {
        clothes.setSex(null);

        testInvalidField(clothes, null,
                "Sex must be included!");
    }

    @ParameterizedTest
    @MethodSource("getInvalidAmount")
    public void testInvalidAvailableAmount(int availableAmount) {
        clothes.setAvailableAmount(availableAmount);

        testInvalidField(clothes, availableAmount,
                "Amount of clothes cannot be lower than 0!");
    }

    private static Stream<Integer> getInvalidAmount() {
        return Stream.of(-1, -5);
    }

    @Test
    public void testNullSizes() {
        clothes.setSizes(null);

        testInvalidField(clothes, null,
                "Sizes must be included!");
    }

    @Test
    public void testNullColors() {
        clothes.setColors(null);

        testInvalidField(clothes, null,
                "Colors must be included!");
    }

    @Test
    public void testNullType() {
        clothes.setType(null);

        testInvalidField(clothes, null,
                "Type of clothes must be included!");
    }
}
