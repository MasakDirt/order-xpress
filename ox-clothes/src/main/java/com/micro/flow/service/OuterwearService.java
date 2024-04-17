package com.micro.flow.service;

import com.micro.flow.domain.Outerwear;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface OuterwearService {

    Page<Outerwear> getAllBySearch(int page, String order,
                                   String searchValue, String... properties);

    Outerwear getOneById(Long id);

    Outerwear create(Outerwear outerwear);

    void delete(Long id);

}
