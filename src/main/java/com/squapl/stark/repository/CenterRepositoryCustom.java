package com.squapl.stark.repository;


import org.springframework.stereotype.Repository;

@Repository
public interface CenterRepositoryCustom {
    public String getCenterWiseInfo(String corporate_id, String center_id);
}

