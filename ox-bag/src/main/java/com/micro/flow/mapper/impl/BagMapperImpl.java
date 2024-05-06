package com.micro.flow.mapper.impl;

import com.micro.flow.client.ClothesServiceFeignClients;
import com.micro.flow.client.UserServiceFeignClient;
import com.micro.flow.domain.Bag;
import com.micro.flow.dto.BagResponse;
import com.micro.flow.dto.clothes.ClothesResponse;
import com.micro.flow.dto.user.UserResponse;
import com.micro.flow.mapper.BagMapper;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Set;

import static java.math.BigDecimal.ZERO;

@AllArgsConstructor
public class BagMapperImpl implements BagMapper {
    private final UserServiceFeignClient userServiceFeignClient;
    private final ClothesServiceFeignClients clothesServiceFeignClients;

    @Override
    public BagResponse getBagResponseFromDomain(Bag bag) {
        BagResponse.BagResponseBuilder bagResponse = BagResponse.builder();

        if (bag.getId() != null) {
            bagResponse.id(bag.getId());
        }

        var totalPrice = bag.getTotalPrice();
        bagResponse.totalPrice(totalPrice == null ? ZERO : totalPrice);

        if (bag.getUserEmail() != null && !bag.getUserEmail().trim().isEmpty()) {
            bagResponse.userResponse(getUserResponse(bag.getUserEmail()));
        }

        if (bag.getClothesIds() != null && !bag.getClothesIds().isEmpty()) {
            bagResponse.clothesResponses(getClothesResponses(bag.getClothesIds()));
        } else {
            bagResponse.clothesResponses(List.of());
        }

        return bagResponse.build();
    }

    private UserResponse getUserResponse(String userEmail) {
        return userServiceFeignClient.getByEmail(userEmail);
    }

    private List<ClothesResponse> getClothesResponses(Set<Long> clothesIds) {
        return clothesServiceFeignClients.getClothesByIds(clothesIds);
    }

}
