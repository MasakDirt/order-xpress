package com.micro.flow.repository;

import com.micro.flow.domain.Bag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BagRepository extends CrudRepository<Bag, UUID> {

    Optional<Bag> findByUsername(String username);

}
