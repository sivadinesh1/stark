package com.squapl.stark.repository;


import com.squapl.stark.model.TrainerDetails;
import com.squapl.stark.model.Users;
import com.squapl.stark.util.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional
public class UserRepositoryImpl implements UserRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    Helper helper;

    public List<Users> getAllProfiles(String center_id, String role_id, String status) {

        Query query = entityManager.createNativeQuery(
                " select u.* " +
                        " from " +
                        " users u, " +
                        " user_role ur " +
                        " where " +
                        " u.id = ur.user_id and " +
                        " u.center_id = ? and " +
                        " u.status = ? and " +
                        " ur.role_id= ? ", Users.class);

        query.setParameter(1, Long.valueOf(center_id));
        query.setParameter(2, status);
        query.setParameter(3, Long.valueOf(role_id));

        // String tempStr = (String) (query.getResultList().get(0));

        return (List<Users>) query.getResultList();

    }

    public String getAllTrainers(String center_id, String role_id, String status) {

        StringBuilder sb = new StringBuilder();

        sb.append("select cast(json_agg(t) as text)");
        sb.append(" from ( ");

        sb.append("select u.*, td.id as tdetailsid, td.trainerfee, td.level as level");
        sb.append(" from ");
        sb.append(" users u,  ");
        sb.append(" user_role ur, ");
        sb.append(" trainer_details td  ");
        sb.append(" where  ");
        sb.append(" u.id = ur.user_id and  ");
        sb.append(" td.userid = u.id and ");
        sb.append(" u.center_id = ? and  ");
        sb.append(" u.status = ? and  ");
        sb.append(" ur.role_id = ?  ");

        sb.append(") t ");

        Query query = entityManager.createNativeQuery(sb.toString());

        query.setParameter(1, Long.valueOf(center_id));
        query.setParameter(2, status);
        query.setParameter(3, Long.valueOf(role_id));

        return (String) query.getSingleResult();
    }


    public int updateTrainerInfo(TrainerDetails trainerDetailsVO) {
        int rowcount = 0;

        StringBuilder sb = new StringBuilder();
        sb.append(" update users set firstname = ? , ");
        sb.append(" email = ?, gender = ? where id = ?");

        try {
            Query query0 = entityManager.createNativeQuery(sb.toString());
            query0.setParameter(1, trainerDetailsVO.getTrainuser().getFirstname());
            query0.setParameter(2, trainerDetailsVO.getTrainuser().getEmail());
            query0.setParameter(3, trainerDetailsVO.getTrainuser().getGender());
            query0.setParameter(4, trainerDetailsVO.getTrainuser().getId());
            rowcount = query0.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in updateTrainerInfo " + e.getMessage());
        }

        return rowcount;
    }

    public int updateMcInfo(Users userVO) {
        int rowcount = 0;

        StringBuilder sb = new StringBuilder();
        sb.append(" update users set firstname = ? , ");
        sb.append(" email = ?, gender = ? where id = ?");

        try {
            Query query0 = entityManager.createNativeQuery(sb.toString());
            query0.setParameter(1, userVO.getFirstname());
            query0.setParameter(2, userVO.getEmail());
            query0.setParameter(3, userVO.getGender());
            query0.setParameter(4, userVO.getId());
            rowcount = query0.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in updateMcInfo " + e.getMessage());
        }

        return rowcount;
    }


    public int updateCAInfo(Users userVO) {
        int rowcount = 0;

        StringBuilder sb = new StringBuilder();
        sb.append(" update users set firstname = ? , ");
        sb.append(" email = ?, gender = ? where id = ?");

        try {
            Query query0 = entityManager.createNativeQuery(sb.toString());
            query0.setParameter(1, userVO.getFirstname());
            query0.setParameter(2, userVO.getEmail());
            query0.setParameter(3, userVO.getGender());
            query0.setParameter(4, userVO.getId());
            rowcount = query0.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in updateMcInfo " + e.getMessage());
        }

        return rowcount;
    }
}


