package com.micro.flow.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;

import java.math.BigDecimal;

import static jakarta.persistence.EnumType.STRING;

@Getter
@Setter
@MappedSuperclass
@ToString(of = "productName")
@EqualsAndHashCode(of = "productName")
public abstract class Clothes {

    @NaturalId
    @NotEmpty(message = "Product name must be included!")
    @Column(name = "product_name", nullable = false)
    private String productName;

    @NotNull(message = "Price must be included!")
    @Column(nullable = false)
    private BigDecimal price;

    @Enumerated(STRING)
    @Column(nullable = false)
    @NotNull(message = "Sex must be included!")
    private Sex sex;

    @Column(nullable = false)
    @NotEmpty(message = "Describe this product!")
    private String description;

    @Column(nullable = false)
    @Min(value = 0, message = "Amount of clothes cannot be lower than 0!")
    private int availableAmount;

    public enum Sex {
        MALE, FEMALE, UNISEX
    }

}
