package com.micro.flow.service.impl;

import com.micro.flow.domain.Clothes;
import com.micro.flow.repository.ClothesRepository;
import com.micro.flow.service.ClothesService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.data.domain.Sort.by;

@AllArgsConstructor
public class ClothesServiceImpl implements ClothesService {
    private final ClothesRepository clothesRepository;

    @Override
    @Cacheable("getAllSortedClothes")
    public Page<Clothes> getAllBySearch(int page, String order,
                                        String searchValue, String... properties) {
        final int pageSize = 4;
        return clothesRepository.findBySearchContaining(
                searchValue, getPageRequest(page, pageSize, order, properties));
    }

    private PageRequest getPageRequest(int page, int pageSize,
                                       String order, String[] properties) {
        return PageRequest.of(page, pageSize,
                by(getDirection(order), properties));
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
        return clothesRepository.findAllById(ids);
    }

    @Override
    public Clothes getOneById(Long id) {
        return clothesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Outerwear not found"));
    }

    @Override
    @CachePut(value = {"getAllSortedClothes", "getAllByIds"})
    public Clothes create(Clothes clothes) {
        return clothesRepository.save(clothes);
    }

    @Override
    @CacheEvict(value = {"getAllSortedClothes", "getAllByIds"}, allEntries = true)
    public void delete(Long id) {
        clothesRepository.delete(getOneById(id));
    }

}
