package com.squapl.stark.repository;


import com.squapl.stark.model.security.Role;
import com.squapl.stark.model.security.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    UserRole findByRole(Role role);


}