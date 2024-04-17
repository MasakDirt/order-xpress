package com.micro.flow.repository;

import com.micro.flow.domain.Socks;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SocksRepository extends JpaRepository<Socks, Long> {

    @Query("select s from Socks s where " +
            "(coalesce(:search_value, '') = '' or " +
            "s.productName =: search_value or " +
            "s.productName like %:search_value% or " +
            "s.description =: search_value or " +
            "s.description like %:search_value%)")
    Page<Socks> findBySearchContaining(@Param("search_value") String searchValue, Pageable pageable);

}
