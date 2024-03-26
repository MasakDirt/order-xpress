package com.micro.flow.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@ToString
public abstract class Clothes {

    @JsonProperty("_id")
    private String id;

    @NotEmpty(message = "Product name must be included!")
    private String productName;

    @NotNull(message = "Price must be included!")
    private BigDecimal price;

    @NotNull(message = "Sex must be included!")
    private Sex sex;

    @NotEmpty(message = "Describe this product!")
    private String description;

    @Min(value = 0, message = "Amount of clothes cannot be lower than 0!")
    private int availableAmount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clothes clothes = (Clothes) o;
        return Objects.equals(id, clothes.id) && Objects.equals(productName, clothes.productName)
                && Objects.equals(price, clothes.price) && sex == clothes.sex
                && Objects.equals(description, clothes.description);
    }

    @Override
    public int hashCode() {
        return 55 * Objects.hash(id, productName, price, sex, description);
    }

    public enum Sex {
        MALE, FEMALE
    }

}
