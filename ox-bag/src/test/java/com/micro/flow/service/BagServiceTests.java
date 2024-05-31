package com.micro.flow.service;

import com.micro.flow.client.ClothesServiceFeignClients;
import com.micro.flow.domain.Bag;
import com.micro.flow.exception.EntityNotFoundException;
import com.micro.flow.repository.BagRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.cassandra.CassandraInvalidQueryException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class BagServiceTests {
    private final BagService bagService;
    private final BagRepository bagRepository;
    private final ClothesServiceFeignClients clothesServiceFeignClients;

    @Autowired
    public BagServiceTests(BagService bagService, BagRepository bagRepository,
                           ClothesServiceFeignClients clothesServiceFeignClients) {
        this.bagService = bagService;
        this.bagRepository = bagRepository;
        this.clothesServiceFeignClients = clothesServiceFeignClients;
    }

    @Test
    public void testCreateSuccess() {
        String username = "new user";
        Bag expected = new Bag(username);
        Bag created = bagService.create(username);
        expected.setId(created.getId());

        assertEquals(expected, created);
    }

    @Test
    public void testCreateIfBagExists() {
        String username = "exists username";
        Bag expected = bagRepository.save(new Bag(username));
        Bag created = bagService.create(username);

        assertEquals(expected, created);
    }

    @Test
    public void testCreateWithNullUsername() {
        String username = null;
        assertThrows(CassandraInvalidQueryException.class, () -> bagService.create(username));
    }

    @Test
    public void testGetByUsernameSuccess() {
        String username = "adminito251";
        Bag expected = bagRepository.save(new Bag(username));
        Bag getBag = bagService.getByUsername(username);

        assertEquals(expected, getBag);
    }

    @Test
    public void testGetByUsernameFail() {
        String username = "invalid";
        assertThrows(EntityNotFoundException.class, () -> bagService.getByUsername(username));
    }

    @Test
    public void testPutClothesToBagSuccess() {
        String username = "usernameforbags";
        Bag bag = bagRepository.save(new Bag(username));
        int clothesIdsBefore = bag.getClothesIds().size();
        bagService.putClothesToBag(username, 12L);
        bagService.putClothesToBag(username, 10L);
        Bag bagAfterPutting = bagService.getByUsername(username);
        Set<Long> clothesIdsAfter = bagAfterPutting.getClothesIds();
        BigDecimal expectedPrice = clothesServiceFeignClients.reduceClothes(clothesIdsAfter);

        assertTrue(clothesIdsBefore < clothesIdsAfter.size());
        assertTrue(bagAfterPutting.getClothesIds().contains(12L));
        assertTrue(bagAfterPutting.getClothesIds().contains(10L));
        assertEquals(expectedPrice, bagAfterPutting.getTotalPrice());
    }

    @Test
    public void testPutClothesToBagNotFound() {
        String username = "notfound";

        assertThrows(EntityNotFoundException.class, () ->
                bagService.putClothesToBag(username, 28L));
    }

    @Test
    public void testDeleteClothesFromBagSuccess() {
        String username = "usernamefordeletingbags";
        bagRepository.save(new Bag(username));
        bagService.putClothesToBag(username, 20L);
        bagService.putClothesToBag(username, 1L);
        Bag bagAfterPutting = bagService.getByUsername(username);
        Set<Long> clothesIdsBefore = bagAfterPutting.getClothesIds();
        assertTrue(bagAfterPutting.getClothesIds().contains(20L));
        assertTrue(bagAfterPutting.getClothesIds().contains(1L));


        bagService.deleteClothesFromBag(username, 20L);
        bagService.deleteClothesFromBag(username, 1L);
        Bag bagAfterDeleting = bagService.getByUsername(username);
        Set<Long> clothesIdsAfterDeleting = bagAfterDeleting.getClothesIds();
        BigDecimal expectedPrice = clothesServiceFeignClients
                .reduceClothes(clothesIdsAfterDeleting);

        assertTrue(clothesIdsBefore.size() > clothesIdsAfterDeleting.size());
        assertEquals(expectedPrice, bagAfterDeleting.getTotalPrice());
    }

    @Test
    public void testDeleteClothesFromBagNotFound() {
        String username = "notfound";

        assertThrows(EntityNotFoundException.class, () ->
                bagService.deleteClothesFromBag(username, 2L));
    }

    @Test
    public void testGetTotalPrice() {
        String username = "gettotalprice";
        BigDecimal expectedTotalPrice = BigDecimal.valueOf(1000);

        Bag bag = new Bag(username);
        bag.setTotalPrice(expectedTotalPrice);
        bagRepository.save(bag);
        BigDecimal actualTotalPrice = bagService.getTotalPriceByUsername(username);

        assertEquals(expectedTotalPrice, actualTotalPrice);
    }

    @Test
    public void testGetTotalPriceNotFound() {
        String username = "notfound";
        assertThrows(EntityNotFoundException.class, () ->
                bagService.getTotalPriceByUsername(username));
    }

    @Test
    public void testResetBagSuccess() {
        String username = "usernameresetbags";
        bagRepository.save(new Bag(username));
        bagService.putClothesToBag(username, 31L);
        bagService.putClothesToBag(username, 15L);
        Bag bagAfterPutting = bagService.getByUsername(username);
        assertFalse(bagAfterPutting.getClothesIds().isEmpty());
        assertTrue(bagAfterPutting.getClothesIds().contains(31L));
        assertTrue(bagAfterPutting.getClothesIds().contains(15L));

        bagService.resetBag(username);
        Bag afterResetting = bagService.getByUsername(username);
        assertTrue(afterResetting.getClothesIds().isEmpty());
        assertEquals(BigDecimal.ZERO, afterResetting.getTotalPrice().ZERO);
    }

    @Test
    public void testResetBagNotFound() {
        String username = "notfound";

        assertThrows(EntityNotFoundException.class, () ->
                bagService.resetBag(username));
    }
}
