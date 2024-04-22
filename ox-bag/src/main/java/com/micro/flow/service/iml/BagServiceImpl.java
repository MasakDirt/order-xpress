package com.micro.flow.service.iml;

import com.micro.flow.client.ClothesServiceFeignClients;
import com.micro.flow.domain.Bag;
import com.micro.flow.dto.clothes.ClothesResponse;
import com.micro.flow.repository.BagRepository;
import com.micro.flow.service.BagService;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
public class BagServiceImpl implements BagService {
    private final BagRepository bagRepository;
    private final ClothesServiceFeignClients serviceFeignClients;

    @Override
    public Bag create(String userEmail) {
        var bag = new Bag();
        bag.setUserEmail(userEmail);

        return bagRepository.save(bag);
    }

    @Override
    public Bag getById(UUID id) {
        return bagRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Bag not found"));
    }

    @Override
    public void putClothesToBag(UUID id, Long clothesId) {
        setPriceAndSave(getById(id).updateClothesIdAndGet(clothesId));
    }

    @Override
    public void deleteClothesFromBag(UUID id, Long clothesId) {
        setPriceAndSave(getById(id).deleteClothesIdAndGet(clothesId));
    }

    private void setPriceAndSave(Bag bag) {
        bag.setTotalPrice(reducePriceForBag(bag.getClothesIds()));
        bagRepository.save(bag);
    }

    private BigDecimal reducePriceForBag(Set<Long> clothesIds) {
        return serviceFeignClients.getClothesByIds(clothesIds)
                .stream()
                .map(ClothesResponse::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
