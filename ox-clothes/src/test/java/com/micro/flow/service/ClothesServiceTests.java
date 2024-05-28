package com.micro.flow.service;

import com.micro.flow.domain.Clothes;
import com.micro.flow.domain.SampleSize;
import com.micro.flow.domain.Sex;
import com.micro.flow.domain.Type;
import com.micro.flow.repository.ClothesRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class ClothesServiceTests {
    private final ClothesRepository clothesRepository;
    private final ClothesService clothesService;

    @Autowired
    public ClothesServiceTests(ClothesRepository clothesRepository, ClothesService clothesService) {
        this.clothesRepository = clothesRepository;
        this.clothesService = clothesService;
    }

    @Test
    public void testGetAllBySearchSuccessAndEmptySearchValue() {
        int page = 0;
        String searchValue = "";
        int defaultPageSize = 4;

        List<Clothes> expected = clothesRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Clothes::getPrice))
                .limit(defaultPageSize)
                .toList();

        List<Clothes> actual = clothesService.getAllBySearch(
                page, "+", searchValue, "price").getContent();

        assertEquals(expected, actual);
    }

    @Test
    public void testGetAllBySearchSuccess() {
        int page = 0;
        String searchValue = "Unisex";
        int defaultPageSize = 4;

        List<Clothes> expected = clothesRepository.findAll()
                .stream()
                .filter(clothes -> clothes.getProductName().contains(searchValue)
                        || clothes.getDescription().contains(searchValue))
                .sorted((o1, o2) -> o2.getId().compareTo(o1.getId()))
                .limit(defaultPageSize)
                .toList();

        List<Clothes> actual = clothesService.getAllBySearch(
                page, "-", searchValue, "id").getContent();

        assertEquals(expected, actual);
    }

    @Test
    public void testGetAllBySearchInvalidOrder() {
        int page = 0;
        String searchValue = "Unisex";

        assertThrows(IllegalArgumentException.class, () -> clothesService.getAllBySearch(
                page, "invalid order", searchValue, "id"));
    }


    @Test
    public void testGetAllBySearchInvalidProperties() {
        int page = 0;
        String searchValue = "Unisex";

        assertThrows(InvalidDataAccessApiUsageException.class, () -> clothesService.getAllBySearch(
                page, "-", searchValue, "invalid"));
    }

    @Test
    public void testGetAllByIdsSuccess() {
        List<Long> ids = List.of(22L, 1L, 2L, 3L, 16L, 26L, 12L);

        List<Clothes> actual = clothesService.getAllByIds(ids);

        assertFalse(actual.isEmpty());
        assertEquals(ids.size(), actual.size());
    }

    @Test
    public void testGetAllByIdsWithInvalidId() {
        List<Long> ids = List.of(22L, 1L, 9L, 16L, 6L, 1000L);

        List<Clothes> actual = clothesService.getAllByIds(ids);

        assertFalse(actual.isEmpty());
        assertTrue(ids.size() > actual.size());
    }

    @Test
    public void testGetAllByIdsWithEmptyIds() {
        List<Long> ids = List.of();

        assertTrue(clothesService.getAllByIds(ids).isEmpty());
    }

    @Test
    public void testGetOneByIdSuccess() {
        Long id = 15L;
        Clothes expected = clothesRepository.findAll()
                .stream()
                .filter(clothes -> clothes.getId().equals(id))
                .findFirst()
                .orElseThrow();

        Clothes actual = clothesService.getOneById(id);
        assertEquals(expected, actual);
    }

    @Test
    public void testGetOneByIdWithInvalidId() {
        Long id = 0L;

        assertThrows(EntityNotFoundException.class, () -> clothesService.getOneById(id));
    }

    @Test
    public void testCreateSuccess() {
        int sizeBeforeCreation = clothesRepository.findAll().size();
        Clothes expected = createClothes("description for socks");
        Clothes actual = clothesService.create(expected);
        expected.setId(actual.getId());
        int sizeAfterCreation = clothesRepository.findAll().size();

        assertTrue(sizeBeforeCreation < sizeAfterCreation);
        assertEquals(expected, actual);
    }

    @Test
    public void testCreateWithInvalidValue() {
        String emptyDescription = "";
        assertThrows(ConstraintViolationException.class, () ->
                clothesService.create(createClothes(emptyDescription)));
    }

    private Clothes createClothes(String description) {
        Clothes clothes = new Clothes();
        clothes.setPrice(BigDecimal.valueOf(120));
        clothes.setSex(Sex.MALE);
        clothes.setProductName("Socks");
        clothes.setDescription(description);
        clothes.setColors(List.of("red", "green", "blue"));
        clothes.setSizes(List.of(SampleSize.XS, SampleSize.L));
        clothes.setAvailableAmount(120);
        clothes.setType(Type.SOCKS);
        clothes.setSellerUsername("someUsername");

        return clothes;
    }

    @Test
    public void testDeleteSuccess() {
        Long id = clothesRepository.findAll()
                .stream()
                .findFirst().orElseThrow().getId();
        int sizeBeforeDeleting = clothesRepository.findAll().size();
        clothesService.delete(id);
        int sizeAfterDeleting = clothesRepository.findAll().size();

        assertTrue(sizeBeforeDeleting > sizeAfterDeleting);
    }

    @Test
    public void testDeleteNotFound() {
        Long id = 0L;

        assertThrows(EntityNotFoundException.class, () -> clothesService.delete(id));
    }

    @Test
    public void testReduceTotalBagForPriceSuccess() {
        List<Long> ids = List.of(2L, 12L, 5L, 25L, 19L, 21L);
        BigDecimal expected = clothesRepository.findAllById(ids)
                .stream()
                .map(Clothes::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal actual = clothesService.reduceTotalPriceForBag(ids);

        assertEquals(expected, actual);
    }

    @Test
    public void testReduceTotalBagForPriceWithEmptyIds() {
        List<Long> ids = List.of();
        BigDecimal expected = BigDecimal.ZERO;
        BigDecimal actual = clothesService.reduceTotalPriceForBag(ids);

        assertEquals(expected, actual);
    }
}
