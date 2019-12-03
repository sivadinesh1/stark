package com.squapl.stark.repository;


import com.squapl.stark.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Long>, UserRepositoryCustom {
    Users findByUsername(String username);

    Users findByMobilenumber(String phone);


}

