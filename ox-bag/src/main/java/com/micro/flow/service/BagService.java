package com.micro.flow.service;

import com.micro.flow.domain.Bag;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public interface BagService {

    Bag create(String username);

    Bag getByUsername(String username);

    void putClothesToBag(String username, Long clothesId);

    void deleteClothesFromBag(String username, Long clothesId);

    BigDecimal getTotalPriceByUsername(String username);

    void resetBag(String username);
}
