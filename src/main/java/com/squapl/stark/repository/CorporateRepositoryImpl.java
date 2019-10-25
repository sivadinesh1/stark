package com.squapl.stark.repository;


import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public class CorporateRepositoryImpl implements CorporateRepositoryCustom {


}