package com.squapl.stark.repository;


import com.squapl.stark.util.Helper;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
@Transactional
public class ServicesRepositoryImpl implements ServicesRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    Helper helper;


    public JSONArray getServices(String status, String center_id, String service_category_id) {

        JSONArray resultJArr = null;
        try {

            StringBuilder sb = new StringBuilder();
            sb.append(" select cast(json_agg(t) as text) ");
            sb.append(" from ( ");
            sb.append(" select * from service where isactive = ? and center_id = ? and service_category_id = ? ");
            sb.append(") t ");

            Query query = entityManager.createNativeQuery(sb.toString());

            query.setParameter(1, status);
            query.setParameter(2, Long.valueOf(center_id));
            query.setParameter(3, Long.valueOf(service_category_id));


            resultJArr = helper.getJSONArray((String) (query.getResultList().get(0)));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in getPackages.." + e.getMessage());
        }

        return resultJArr;

    }
}


