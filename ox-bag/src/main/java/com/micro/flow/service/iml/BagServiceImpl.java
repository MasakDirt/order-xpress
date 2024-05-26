package com.micro.flow.service.iml;

import com.micro.flow.client.ClothesServiceFeignClients;
import com.micro.flow.domain.Bag;
import com.micro.flow.exception.EntityNotFoundException;
import com.micro.flow.repository.BagRepository;
import com.micro.flow.service.BagService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class BagServiceImpl implements BagService {
    private final BagRepository bagRepository;
    private final ClothesServiceFeignClients clothesServiceFeignClients;

    @Override
    public Bag create(String username) {
        if (isBagNotExistByUsername(username)) {
            return saveNewBagWithUsername(username);
        }
        log.info("User with name {} already had bag.", username);
        return getByUsername(username);
    }

    private boolean isBagNotExistByUsername(String userEmail) {
        return bagRepository.findByUsername(userEmail).isEmpty();
    }

    private Bag saveNewBagWithUsername(String username) {
        var bag = bagRepository.save(new Bag(username));
        log.info("User with name {} hadn't bag. Created new bag for him.", username);
        return bag;
    }

    @Override
    public Bag getById(UUID id) {
        var bag = bagRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Bag not found"));
        log.info("Get bag by id: {}", id);
        return bag;
    }

    @Override
    public Bag getByUsername(String username) {
        return bagRepository.findByUsername((username)).orElseThrow(
                () -> new EntityNotFoundException("Bag not found for username: " + username));
    }

    @Override
    public void putClothesToBag(String username, Long clothesId) {
        setPriceAndSave(getByUsername(username).updateClothesIdAndGet(clothesId));
        log.info("Put clothe to bag with username: {}", username);
    }

    @Override
    public void deleteClothesFromBag(String username, Long clothesId) {
        setPriceAndSave(getByUsername(username).deleteClothesIdAndGet(clothesId));
        log.info("Delete clothe from bag with username: {}", username);
    }

    private void setPriceAndSave(Bag bag) {
        bag.setTotalPrice(clothesServiceFeignClients.reduceClothes(bag.getClothesIds()));
        bagRepository.save(bag);
    }


    @Override
    public BigDecimal getTotalPriceByUsername(String username) {
        var totalPrice = getByUsername(username).getTotalPrice();
        log.info("Giving total price: {} for bag with username: {} ",
                totalPrice, username);
        return totalPrice;
    }

    @Override
    public void resetBag(String username) {
        var bag = getByUsername(username);
        bag.resetClothesIds();
        log.info("Empty bag with username: {}", username);
        bagRepository.save(bag);
    }
}
