package com.micro.flow.dto;

import com.micro.flow.domain.SampleSize;
import com.micro.flow.domain.Type;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

import static jakarta.persistence.EnumType.STRING;

@Data
@Builder
@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ClothesCreateRequest {

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

    @Enumerated(STRING)
    @NotNull(message = "Sizes must be included!")
    private List<SampleSize> sizes;

    @NotNull(message = "Colors must be included!")
    private List<String> colors;

    @Enumerated(STRING)
    @NotNull(message = "Type of outerwear must be included!")
    private Type type;

    @NotEmpty(message = "Seller username cannot be empty!")
    private String sellerUsername;
}
