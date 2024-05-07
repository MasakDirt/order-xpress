package com.micro.flow.dto;

import com.micro.flow.domain.User;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponse {

    private Long id;

    @NotNull
    @Min(value = 0, message = "Your balance can't be lower than zero!")
    private BigDecimal balance;

    private User user;

}
