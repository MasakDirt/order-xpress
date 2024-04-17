package com.micro.flow.repository;

import com.micro.flow.domain.Outerwear;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OuterwearRepository extends JpaRepository<Outerwear, Long> {

    @Query("select o from Outerwear o where " +
            "(coalesce(:search_value, '') = '' or " +
            "o.productName =: search_value or " +
            "o.productName like %:search_value% or " +
            "o.description =: search_value or " +
            "o.description like %:search_value%)")
    Page<Outerwear> findBySearchContaining(@Param("search_value") String searchValue, Pageable pageable);

}
