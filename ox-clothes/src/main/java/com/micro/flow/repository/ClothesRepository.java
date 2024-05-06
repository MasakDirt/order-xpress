package com.micro.flow.repository;

import com.micro.flow.domain.Clothes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClothesRepository extends JpaRepository<Clothes, Long> {

    @Query("select c from Clothes c where " +
            "(coalesce(:search_value, '') = '' or " +
            "c.productName =: search_value or " +
            "c.productName like %:search_value% or " +
            "c.description =: search_value or " +
            "c.description like %:search_value%)")
    Page<Clothes> findBySearchContaining(@Param("search_value") String searchValue, Pageable pageable);

}
