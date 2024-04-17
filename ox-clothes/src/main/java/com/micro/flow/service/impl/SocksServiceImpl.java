package com.micro.flow.service.impl;

import com.micro.flow.domain.Socks;
import com.micro.flow.repository.SocksRepository;
import com.micro.flow.service.SocksService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;

import static com.micro.flow.component.ServiceAdvisor.getPageRequest;

@AllArgsConstructor
public class SocksServiceImpl implements SocksService {
    private final SocksRepository socksRepository;

    @Override
    @Cacheable("getAllSortedSocks")
    public Page<Socks> getAllBySearch(int page, String order,
                                      String searchValue, String... properties) {
        final int pageSize = 4;
        return socksRepository.findBySearchContaining(
                searchValue, getPageRequest(page, pageSize, order, properties));
    }

    @Override
    public Socks getOneById(Long id) {
        return socksRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Socks not found"));
    }

    @Override
    @CachePut(value = "getAllSortedSocks")
    public Socks create(Socks socks) {
        return socksRepository.save(socks);
    }

    @Override
    @CacheEvict(value = "getAllSortedSocks", allEntries = true)
    public void delete(Long id) {
        socksRepository.delete(getOneById(id));
    }

}
