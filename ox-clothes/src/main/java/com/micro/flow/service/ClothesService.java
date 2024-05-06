package com.micro.flow.service;

import com.micro.flow.domain.Clothes;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClothesService {

    Page<Clothes> getAllBySearch(int page, String order,
                                 String searchValue, String... properties);

    List<Clothes> getAllByIds(List<Long> ids);

    Clothes getOneById(Long id);

    Clothes create(Clothes clothes);

    void delete(Long id);

}
