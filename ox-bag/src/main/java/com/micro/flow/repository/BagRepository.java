package com.micro.flow.repository;

import com.micro.flow.domain.Bag;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface BagRepository extends CrudRepository<Bag, UUID> {

    Optional<Bag> findByUsername(String username);

}
