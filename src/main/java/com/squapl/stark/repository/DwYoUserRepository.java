package com.squapl.stark.repository;

import com.squapl.stark.model.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DwYoUserRepository extends CrudRepository<Users, Integer> {

    Users findByUsername(String username);

}