package com.micro.flow.repository;

import com.micro.flow.domain.Outerwear;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OuterwearRepository extends MongoRepository<Outerwear, String> {
}
