package com.micro.flow.service;

import com.micro.flow.domain.Socks;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface SocksService {

    Page<Socks> getAllBySearch(int page, String order,
                               String searchValue, String... properties);

    Socks getOneById(Long id);

    Socks create(Socks socks);

    void delete(Long id);

}
