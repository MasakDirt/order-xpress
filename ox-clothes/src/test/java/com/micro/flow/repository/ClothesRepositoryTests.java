package com.micro.flow.repository;

import com.micro.flow.domain.Clothes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.data.domain.Sort.by;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class ClothesRepositoryTests {
    private final ClothesRepository clothesRepository;

    @Autowired
    public ClothesRepositoryTests(ClothesRepository clothesRepository) {
        this.clothesRepository = clothesRepository;
    }

    @Test
    public void testAscFindBySearchContainingSuccess() {
        int page = 0;
        String searchValue = "Male";
        int pageSize = 6;

        List<Clothes> expected = clothesRepository.findAll()
                .stream()
                .filter(clothes -> clothes.getProductName().contains(searchValue)
                        || clothes.getDescription().contains(searchValue))
                .sorted(Comparator.comparing(Clothes::getId))
                .limit(pageSize)
                .toList();


        Pageable pageable = PageRequest.of(page, pageSize, by(Sort.Direction.ASC, "id"));
        List<Clothes> actual = clothesRepository.findBySearchContaining(searchValue, pageable).getContent();

        assertEquals(expected, actual);
    }

    @Test
    public void testDescFindBySearchContainingSuccess() {
        int page = 0;
        String searchValue = "Jacket";
        int pageSize = 6;

        List<Clothes> expected = clothesRepository.findAll()
                .stream()
                .filter(clothes -> clothes.getProductName().contains(searchValue)
                        || clothes.getDescription().contains(searchValue))
                .sorted((o1, o2) -> o2.getProductName().compareTo(o1.getProductName()))
                .limit(pageSize)
                .toList();

        Pageable pageable = PageRequest.of(page, pageSize, by(Sort.Direction.DESC, "productName"));
        List<Clothes> actual = clothesRepository.findBySearchContaining(searchValue, pageable).getContent();

        assertEquals(expected, actual);
    }

    @Test
    public void testEmptyFindBySearchContaining() {
        int page = 0;
        int pageSize = 10;
        String searchValue = "not found type of clothes";

        Pageable pageable = PageRequest.of(page, pageSize, by(Sort.Direction.ASC, "id"));
        Page<Clothes> actual = clothesRepository.findBySearchContaining(searchValue, pageable);
        assertTrue(actual.getContent().isEmpty());
    }
}
