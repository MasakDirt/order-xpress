package com.micro.flow.repository;

import com.micro.flow.domain.Socks;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SocksRepository extends MongoRepository<Socks, String> {
}
