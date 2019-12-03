package com.squapl.stark.repository;


import org.json.simple.JSONArray;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicesRepositoryCustom {
    public JSONArray getServices(String status, String center_id, String service_category_id);
}



