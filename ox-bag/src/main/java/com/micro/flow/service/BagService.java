package com.micro.flow.service;

import com.micro.flow.domain.Bag;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface BagService {

    Bag create(String userEmail);

    Bag getById(UUID id);

    void putClothesToBag(UUID id, Long clothesId);

    void deleteClothesFromBag(UUID id, Long clothesId);

}
