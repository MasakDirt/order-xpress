package com.micro.flow.service.impl;

import com.micro.flow.domain.Clothes;
import com.micro.flow.repository.ClothesRepository;
import com.micro.flow.service.ClothesService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.data.domain.Sort.by;

@Slf4j
@Service
@AllArgsConstructor
public class ClothesServiceImpl implements ClothesService {
    private final ClothesRepository clothesRepository;

    @Override
    public Page<Clothes> getAllBySearch(int page, String order,
                                        String searchValue, String... properties) {
        var clothesPage = clothesRepository.findBySearchContaining(
                searchValue, getPageRequest(page, order, properties));
        log.info("Getting clothes by search {} and properties {}", searchValue, properties);
        return clothesPage;
    }

    private PageRequest getPageRequest(int page, String order, String[] properties) {
        final int pageSize = 4;
        return PageRequest.of(page, pageSize, by(getDirection(order), properties));
    }

    private Sort.Direction getDirection(String order) {
        return switch (order) {
            case "+" -> ASC;
            case "-" -> DESC;
            default -> throw new IllegalArgumentException("Invalid order: " + order);
        };
    }

    @Override
    @Cacheable("getAllByIds")
    public List<Clothes> getAllByIds(List<Long> ids) {
        var clothesByIds = clothesRepository.findAllById(ids);
        log.info("Getting clothes by ids {}", ids);
        return clothesByIds;
    }

    @Override
    public Clothes getOneById(Long id) {
        var clothes = clothesRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Clothes not found"));
        log.info("Getting clothes by id {}", id);
        return clothes;
    }

    @Override
    @CachePut(value = "getAllByIds", key = "#clothes.id")
    public Clothes create(Clothes clothes) {
        var created = clothesRepository.save(clothes);
        log.info("Saving clothes {}", created);
        return created;
    }

    @Override
    @CacheEvict(value = "getAllByIds", key = "#id")
    public void delete(Long id) {
        clothesRepository.delete(getOneById(id));
        log.info("Deleted clothes {}", id);
    }

    @Override
    public BigDecimal reduceTotalPriceForBag(List<Long> clothesIds) {
        return clothesRepository.findAllById(clothesIds)
                .stream()
                .map(Clothes::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
