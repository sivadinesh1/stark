package com.squapl.stark.repository;


import com.squapl.stark.model.Center;
import com.squapl.stark.model.Services;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicesRepository extends JpaRepository<Services, Long>, ServicesRepositoryCustom {
    List<Services> findAllByIsactiveAndCenter(String status, Center center);

}