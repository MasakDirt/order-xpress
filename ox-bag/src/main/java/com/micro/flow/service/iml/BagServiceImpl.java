package com.micro.flow.service.iml;

import com.micro.flow.client.ClothesServiceFeignClients;
import com.micro.flow.domain.Bag;
import com.micro.flow.dto.clothes.ClothesResponse;
import com.micro.flow.repository.BagRepository;
import com.micro.flow.service.BagService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
public class BagServiceImpl implements BagService {
    private final BagRepository bagRepository;
    private final ClothesServiceFeignClients serviceFeignClients;

    @Override
    public Bag create(String userEmail) {
        ifBagNotExistCreateNew(userEmail);
        return bagRepository.findByUserEmail(userEmail).get();
    }

    private void ifBagNotExistCreateNew(String userEmail) {
        if (isBagNotExistByUserEmail(userEmail)) {
            saveNewBagWithUserEmail(userEmail);
            log.info("User with email {} hadn't bag. Created new bag for him.", userEmail);
        }
    }

    private boolean isBagNotExistByUserEmail(String userEmail) {
        return bagRepository.findByUserEmail(userEmail).isEmpty();
    }

    private void saveNewBagWithUserEmail(String userEmail) {
        var bag = new Bag();
        bag.setUserEmail(userEmail);
        bagRepository.save(bag);
    }

    @Override
    public Bag getById(UUID id) {
        var bag = bagRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Bag not found"));
        log.info("Get bag by id: {}", id);
        return bag;
    }

    @Override
    public void putClothesToBag(UUID id, Long clothesId) {
        setPriceAndSave(getById(id).updateClothesIdAndGet(clothesId));
        log.info("Put clothe to bag with id: {}", id);
    }

    @Override
    public void deleteClothesFromBag(UUID id, Long clothesId) {
        setPriceAndSave(getById(id).deleteClothesIdAndGet(clothesId));
        log.info("Delete clothe from bag with id: {}", id);
    }

    private void setPriceAndSave(Bag bag) {
        bag.setTotalPrice(reduceTotalPriceForBag(bag.getClothesIds()));
        bagRepository.save(bag);
    }

    private BigDecimal reduceTotalPriceForBag(Set<Long> clothesIds) {
        return serviceFeignClients.getClothesByIds(clothesIds)
                .stream()
                .map(ClothesResponse::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
