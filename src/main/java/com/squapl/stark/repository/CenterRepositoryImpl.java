package com.squapl.stark.repository;


import com.squapl.stark.util.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
@Transactional
public class CenterRepositoryImpl implements CenterRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    Helper helper;


    public String getCenterWiseInfo(String corporate_id, String center_id) {
        System.out.println("corp ID " + corporate_id);
        System.out.println("center_id ID " + center_id);


        StringBuilder sb = new StringBuilder();
        sb.append("select cast(json_agg(t) as text)");
        sb.append(" from ( ");

        sb.append(" SELECT  ");
        sb.append(" u.center_id as center_id,  ");
        sb.append(" cent_det.name as center_name,  ");
        sb.append(" COUNT(ur.role_id)filter(where ur.role_id='4')as trainers,  ");
        sb.append(" COUNT(ur.role_id)filter(where ur.role_id='5')as members,  ");
        sb.append(" count(ur.role_id)filter(where ur.role_id='6')as center_admin,  ");
        sb.append(" COUNT(ur.role_id)filter(where ur.role_id='3')as member_coordinators  ");

        sb.append(" FROM  ");
        sb.append(" users u,  ");
        sb.append(" user_role ur,  ");
        sb.append(" corporate_details cd,  ");
        sb.append(" center_details cent_det  ");

        sb.append(" WHERE  ");
        sb.append(" ur.user_id=u.id and  ");
        sb.append(" cent_det.id=u.center_id and  ");
        sb.append(" cd.id=cent_det.corporate_id and  ");

        if (corporate_id == null) {
            sb.append(" cent_det.id = ?  ");
        } else if (center_id == null) {
            sb.append(" cent_det.corporate_id= ?  ");
        }

        sb.append(" group by  ");
        sb.append(" u.center_id,cent_det.name  ");
        sb.append(" order by  ");
        sb.append(" cent_det.name  ");

        sb.append(") t ");

        Query query = entityManager.createNativeQuery(sb.toString());

        if (corporate_id == null) {
            query.setParameter(1, Long.valueOf(center_id));
        } else if (center_id == null) {
            query.setParameter(1, Long.valueOf(corporate_id));
        }


        return (String) query.getSingleResult();
    }


}


