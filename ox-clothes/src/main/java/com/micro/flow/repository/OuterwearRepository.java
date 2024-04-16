package com.micro.flow.repository;

import com.micro.flow.domain.Outerwear;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OuterwearRepository extends JpaRepository<Outerwear, String> {
}
