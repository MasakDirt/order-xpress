package com.micro.flow.service.iml;

import com.micro.flow.client.ClothesServiceFeignClients;
import com.micro.flow.domain.Bag;
import com.micro.flow.dto.clothes.ClothesResponse;
import com.micro.flow.repository.BagRepository;
import com.micro.flow.service.BagService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class BagServiceImpl implements BagService {
    private final BagRepository bagRepository;
    private final ClothesServiceFeignClients clothesServiceFeignClients;

    @Override
    public Bag create(String username) {
        ifBagNotExistCreateNew(username);
        return bagRepository.findByUsername(username).get();
    }

    private void ifBagNotExistCreateNew(String username) {
        if (isBagNotExistByUsername(username)) {
            saveNewBagWithUsername(username);
            log.info("User with name {} hadn't bag. Created new bag for him.", username);
            return;
        }
        log.info("User with name {} already had bag.", username);
    }

    private boolean isBagNotExistByUsername(String userEmail) {
        return bagRepository.findByUsername(userEmail).isEmpty();
    }

    private void saveNewBagWithUsername(String username) {
        var bag = new Bag();
        bag.setUsername(username);
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
        return clothesServiceFeignClients.getClothesByIds(clothesIds)
                .stream()
                .map(ClothesResponse::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal getTotalPrice(UUID id) {
        var totalPrice = getById(id).getTotalPrice();
        log.info("Giving total price: {} for bag with id: {} ", totalPrice, id);
        return totalPrice;
    }

    @Override
    public void resetBag(UUID uuid) {
        var bag = getById(uuid);
        bag.resetClothesIds();
        log.info("Empty bag with id: {}", uuid);
        bagRepository.save(bag);
    }
}
