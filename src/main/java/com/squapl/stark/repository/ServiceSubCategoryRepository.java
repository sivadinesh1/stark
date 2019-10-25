package com.squapl.stark.repository;


import com.squapl.stark.model.Center;
import com.squapl.stark.model.ServiceSubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceSubCategoryRepository extends JpaRepository<ServiceSubCategory, Long>, ServiceSubCategoryRepositoryCustom {

    List<ServiceSubCategory> findAllByIsactive(String status);

    List<ServiceSubCategory> findAllByIsactiveAndCenter(String status, Center center);

}