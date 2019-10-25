package com.squapl.stark.service.serviceimpl;

import com.squapl.stark.service.GeneralService;
import com.squapl.stark.util.Helper;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@Service
@Transactional
@Slf4j
public class GeneralImpl implements GeneralService {
    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    Helper helper;


    public void captureError() {

    }

//    TODO - INCLUDE STATUS CHECK ISACTIVE ETC.. IMPORTANT


    public JSONArray getCenterWiseInfo(String corporate_id, String center_id) {
        System.out.println("corp ID " + corporate_id);
        System.out.println("center_id ID " + center_id);

        JSONArray resultJArr = null;


        StringBuilder sb = new StringBuilder();
        sb.append("select cast(array_to_json(array_agg(row_to_json(t))) as text)");
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

        try {

            Query query = entityManager.createNativeQuery(sb.toString());

            if (corporate_id == null) {
                query.setParameter(1, Long.valueOf(center_id));
            } else if (center_id == null) {
                query.setParameter(1, Long.valueOf(corporate_id));
            }


            String tempStr = (String) (query.getResultList().get(0));

            if (tempStr != null) {
                resultJArr = helper.getJSONArray(tempStr);
            }
            System.out.println("><<><><><<" + resultJArr);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in getCenterWiseInfo " + e.getMessage());

        }
        return resultJArr;
    }


    public JSONArray getAllProfiles(String center_id, String role_id, String status) {

        System.out.println("center_id " + center_id);
        System.out.println("role_id " + role_id);
        System.out.println("status " + status);

        JSONArray resultJArr = null;

        try {

            Query query = entityManager.createNativeQuery(" select cast(array_to_json(array_agg(row_to_json(t))) as text) " +
                    " from ( " +

                    " select u.* " +
                    " from " +
                    " users u, " +
                    " user_role ur " +
                    " where " +
                    " u.id = ur.user_id and " +
                    " u.center_id = ? and " +
                    " u.status = ? and " +
                    " ur.role_id= ? " +

                    ") t ");

            query.setParameter(1, Long.valueOf(center_id));
            query.setParameter(2, status);
            query.setParameter(3, Long.valueOf(role_id));

            String tempStr = (String) (query.getResultList().get(0));

            if (tempStr != null) {
                resultJArr = helper.getJSONArray(tempStr);
            }
            System.out.println("><<><><><<" + resultJArr);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in getCenterWiseInfo " + e.getMessage());
        }
        return resultJArr;
    }


}
