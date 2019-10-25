package com.squapl.stark.repository;


import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional(readOnly = true)
public class ServiceCategoryRepositoryImpl implements ServiceCategoryRepositoryCustom {


    @Override
    public void testFunction() {
        System.out.println("inside test function");

    }

}