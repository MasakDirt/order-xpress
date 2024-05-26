package com.micro.flow.repository;

import com.micro.flow.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @Query("select distinct u from User u left join fetch u.roles where u.username = :username")
    Optional<User> findByUsername(@Param("username") String username);

}
