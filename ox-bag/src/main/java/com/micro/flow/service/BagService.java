package com.micro.flow.service;

import com.micro.flow.domain.Bag;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public interface BagService {

    Bag create(String userEmail);

    Bag getById(UUID id);

    Bag getByUsername(String username);

    void putClothesToBag(String username, Long clothesId);

    void deleteClothesFromBag(String username, Long clothesId);

    BigDecimal getTotalPriceByUsername(String username);

    void resetBag(String username);
}
