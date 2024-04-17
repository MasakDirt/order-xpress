package com.micro.flow.dto.socks;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Data
@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
public class ShortenSocksResponse {

    private Long id;

    @NotEmpty(message = "Product name must be included!")
    private String productName;

    @NotNull(message = "Price must be included!")
    private BigDecimal price;

    @NotEmpty(message = "Sex must be included!")
    private String sex;

    @NotEmpty(message = "Size must be include!")
    private String size;

    @Min(value = 0, message = "Available colors cannot be lower than 0!")
    private int availableColors;

}
