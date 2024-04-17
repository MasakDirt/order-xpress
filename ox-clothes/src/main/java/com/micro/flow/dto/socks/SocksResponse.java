package com.micro.flow.dto.socks;

import com.micro.flow.domain.Clothes;
import com.micro.flow.domain.SampleSize;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

import static jakarta.persistence.EnumType.STRING;

@Data
@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
public class SocksResponse {
    private Long id;

    @NotEmpty(message = "Product name must be included!")
    private String productName;

    @NotNull(message = "Price must be included!")
    private BigDecimal price;

    @Enumerated(STRING)
    @NotNull(message = "Sex must be included!")
    private Clothes.Sex sex;

    @NotEmpty(message = "Describe this product!")
    private String description;

    @Min(value = 0, message = "Amount of clothes cannot be lower than 0!")
    private int availableAmount;

    @Enumerated(STRING)
    @NotNull(message = "Size must be include!")
    private SampleSize size;

    @NotEmpty(message = "Colors must be include!")
    private List<String> colors;

}
