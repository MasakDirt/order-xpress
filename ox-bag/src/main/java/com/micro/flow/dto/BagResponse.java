package com.micro.flow.dto;

import com.micro.flow.dto.clothes.ClothesResponse;
import com.micro.flow.dto.user.UserResponse;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@Getter
@Setter
public class BagResponse {

    private UUID id;

    @NotNull(message = "Total price cannot be null!")
    @Min(value = 0, message = "Total price cannot be lower that 0!")
    private BigDecimal totalPrice;

    @NotNull(message = "User cannot be null!")
    private UserResponse userResponse;

    private List<ClothesResponse> clothesResponses;
}
