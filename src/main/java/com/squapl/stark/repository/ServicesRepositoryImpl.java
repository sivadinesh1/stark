package com.squapl.stark.repository;


import com.squapl.stark.util.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Transactional
public class ServicesRepositoryImpl implements ServicesRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    Helper helper;


}


