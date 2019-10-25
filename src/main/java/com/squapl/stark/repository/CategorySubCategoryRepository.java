package com.squapl.stark.repository;


import com.squapl.stark.model.CategorySubCategory;
import com.squapl.stark.model.Center;
import com.squapl.stark.model.ServiceCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategorySubCategoryRepository extends JpaRepository<CategorySubCategory, Long> {

    Long deleteByServiceCategory(ServiceCategory serviceCategory);


}