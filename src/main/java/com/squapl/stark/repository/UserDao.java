package com.squapl.stark.repository;


import com.squapl.stark.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends CrudRepository<User, Integer> {

    User findByUsername(String username);

    User findById(Long id);

    User findByMobilenumber(String phone);


}

