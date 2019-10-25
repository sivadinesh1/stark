package com.squapl.stark.repository;


import com.squapl.stark.model.Center;
import com.squapl.stark.model.ServiceCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceCategoryRepository extends JpaRepository<ServiceCategory, Long>, ServiceCategoryRepositoryCustom {

    List<ServiceCategory> findAllByIsactive(String status);

    List<ServiceCategory> findAllByIsactiveAndCenter(String status, Center center);

}