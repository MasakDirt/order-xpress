package com.micro.flow.dto.outerwear;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class OuterwearResponse {

    private Long id;

    @NotEmpty(message = "Product name must be included!")
    private String productName;

    @NotNull(message = "Price must be included!")
    private BigDecimal price;

    @NotNull(message = "Sex must be included!")
    private String sex;

    @NotEmpty(message = "Describe this product!")
    private String description;

    @Min(value = 0, message = "Amount of clothes cannot be lower than 0!")
    private int availableAmount;

    @NotNull(message = "Sizes must be included!")
    private List<String> availableSizes;

    @NotNull(message = "Colors must be included!")
    private List<String> colors;

    @NotNull(message = "Type of outerwear must be included!")
    private String type;
}
