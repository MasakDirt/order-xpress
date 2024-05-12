package com.micro.flow.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponse {

    private Long id;

    @NotNull
    @NumberFormat(pattern = "0.00")
    @Min(value = 0, message = "Your balance can't be lower than zero!")
    private BigDecimal balance;

    @Valid
    private UserDtoForAccount user;

}
