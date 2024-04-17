package com.micro.flow.service.impl;

import com.micro.flow.domain.Outerwear;
import com.micro.flow.repository.OuterwearRepository;
import com.micro.flow.service.OuterwearService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;

import static com.micro.flow.component.ServiceAdvisor.getPageRequest;

@AllArgsConstructor
public class OuterwearServiceImpl implements OuterwearService {
    private final OuterwearRepository outerwearRepository;

    @Override
    @Cacheable("getAllSortedOuterwears")
    public Page<Outerwear> getAllBySearch(int page, String order,
                                          String searchValue, String... properties) {
        final int pageSize = 4;
        return outerwearRepository.findBySearchContaining(
                searchValue, getPageRequest(page, pageSize, order, properties));
    }

    @Override
    public Outerwear getOneById(Long id) {
        return outerwearRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Outerwear not found"));
    }

    @Override
    @CachePut(value = "getAllSortedOuterwears")
    public Outerwear create(Outerwear outerwear) {
        return outerwearRepository.save(outerwear);
    }

    @Override
    @CacheEvict(value = "getAllSortedOuterwears", allEntries = true)
    public void delete(Long id) {
        outerwearRepository.delete(getOneById(id));
    }

}
