package com.squapl.stark.service.serviceimpl;

import com.squapl.stark.model.RawUser;
import com.squapl.stark.model.User;
import com.squapl.stark.model.security.UserRole;
import com.squapl.stark.repository.RoleDao;
import com.squapl.stark.repository.UserRepository;
import com.squapl.stark.service.DwUtilService;
import com.squapl.stark.util.Helper;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.Set;


@Service
@Transactional
@Slf4j
public class DwUtilImpl implements DwUtilService {
    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    Helper helper;


    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleDao roleDao;


    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public int deleteDWUserTbl(String centerid) {
        int rowcount = 0;
        try {
            Query query0 = entityManager.createNativeQuery(" delete from dw_user where source_centerid = ? ");
            query0.setParameter(1, centerid);
            rowcount = query0.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in deleteYoUserTbl " + e.getMessage());
        }

        return rowcount;
    }


    public String getCenterId(String source_centerid) {

        String centerid = "";
        try {
            Query query = entityManager.createNativeQuery(" select sys_value from dw_mappings where col_name = 'centerid' and ext_value= ? ");
            query.setParameter(1, source_centerid);
            centerid = (String) query.getSingleResult();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in deleteYoUserTbl " + e.getMessage());
        }

        return centerid;

    }


    public int addRawUser(RawUser rawUser) {


        int rowcount = 0;

        try {
            Query query = entityManager.createNativeQuery(" insert into dw_user (source_centerid, centerid, firstname, mobilenumber, servicevariation, startdate, enddate, isactive, lastrun) " +
                    "values (?, ?, ?, ?, ?, ?, ?, ?, ?) ");

            query.setParameter(1, rawUser.getSource_centerid());
            query.setParameter(2, rawUser.getCenterid());
            query.setParameter(3, rawUser.getFirstname());
            query.setParameter(4, rawUser.getMobilenumber());
            query.setParameter(5, rawUser.getServicevariation());
            query.setParameter(6, rawUser.getStartdate());
            query.setParameter(7, rawUser.getEnddate());
            query.setParameter(8, rawUser.getIsactive());
            query.setParameter(9, new Date());

            rowcount = query.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in addRawUser " + e.getMessage());
        }


        return rowcount;

    }

    public int updateMigrationStatus(String agent, String event, String entity, String status) {
        System.out.println("inside add Yo User");
        int rowcount = 0;

        try {

            Query query = entityManager.createNativeQuery(" insert into dw_migration (agent, event, entity, status, lastrun) " +
                    "values (?, ?, ?, ?, ?) ");

            query.setParameter(1, agent);
            query.setParameter(2, event);
            query.setParameter(3, entity);
            query.setParameter(4, status);
            query.setParameter(5, new Date());

            rowcount = query.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in updateMigrationStatus " + e.getMessage());
        }

        return rowcount;
    }


    public JSONArray getActiveMemberServies(String userid) {
        System.out.println("user id >> " + userid);
        JSONArray resultJArr = null;

        try {
            Query query = entityManager.createNativeQuery(" select cast(array_to_json(array_agg(row_to_json(t))) as text) " +
                    " from ( " +
                    " select ms.id, ms.userid as user_id, " +
                    " cust_user.center_id, " +
                    " ms.service_id as service_id, " +
                    " ms.trainer_id as trainer_id, " +
                    " s.name as service_name, " +
                    " sc.id as service_category_id, " +
                    " sc.name as service_category_name " +
                    " from " +
                    " member_services ms, " +
                    " users cust_user, " +
                    " service_details sd, " +
                    " service s, " +
                    " service_category sc " +
                    " where " +
                    " sd.service_id = ms.service_id and " +
                    " s.id = ms.service_id and " +
                    " sc.id = s.service_category_id and " +
                    " ms.userid = cust_user.id and " +
                    " cust_user.id = " + userid +
                    " ) t ");


            resultJArr = helper.getJSONArray((String) (query.getResultList().get(0)));
            System.out.println("increment >> " + resultJArr);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in getActiveMemberServies " + e.getMessage());
        }


        return resultJArr;

    }


    public JSONArray getTrainersActiveServies(String userid) {

        JSONArray resultJArr = null;

        try {
            Query query = entityManager.createNativeQuery(" select cast(array_to_json(array_agg(row_to_json(t))) as text) " +
                    " from ( " +

                    " select " +
                    " ms.service_id, " +
                    " s.name, " +
                    " sc.description, " +
                    " train_user.firstname as trainer, " +
                    " cust_user.firstname as member " +
                    " from " +
                    " member_services ms, " +
                    " users train_user, " +
                    " users cust_user, " +
                    " service as s, " +
                    " service_category sc " +
                    " where " +
                    " s.id = ms.service_id and " +
                    " ms.userid = cust_user.id and " +
                    " sc.id = s.service_category and " +
                    " ms.trainer_id = train_user.id and " +
                    " train_user.id = " + userid +


                    " ) t ");

            resultJArr = helper.getJSONArray((String) (query.getResultList().get(0)));
            System.out.println("increment >> " + resultJArr);

        } catch (Exception e) {
            System.out.println("Error in getTrainers " + e.getMessage());
        }


        return resultJArr;

    }

    public JSONArray getServiceLogs(String userid, String service_id) {

        JSONArray resultJArr = null;

        try {
            Query query = entityManager.createNativeQuery(" select cast(array_to_json(array_agg(row_to_json(t))) as text) " +
                    " from ( " +

                    " select * from " +
                    " service_tracker st, " +
                    " member_services ms, " +
                    " users cust_user, " +
                    " users train_user " +
                    " where " +
                    " ms.id = st.member_services_id and " +
                    " cust_user.id = ms.userid and " +
                    " train_user.id = ms.userid and " +
                    " ms.service_id = " + service_id + " and " +
                    " cust_user.id = " + userid +

                    " ) t ");

            resultJArr = helper.getJSONArray((String) (query.getResultList().get(0)));
            System.out.println("increment >> " + resultJArr);

        } catch (Exception e) {
            System.out.println("Error in getTrainers " + e.getMessage());
        }


        return resultJArr;

    }

    public JSONArray getPaymentStatement(String userid, String service_id) {

        JSONArray resultJArr = null;

        try {
            Query query = entityManager.createNativeQuery(" select cast(array_to_json(array_agg(row_to_json(t))) as text) " +
                    " from ( " +

                    " select * from " +
                    " member_services_pymt mps, " +
                    " member_services ms " +
                    " where " +
                    " ms.id = mps.msid and " +
                    " ms.userid = " + userid +

                    " ) t ");

            resultJArr = helper.getJSONArray((String) (query.getResultList().get(0)));
            System.out.println("increment >> " + resultJArr);

        } catch (Exception e) {
            System.out.println("Error in getTrainers " + e.getMessage());
        }


        return resultJArr;

    }

    public User createUser(User user, Set<UserRole> userRoles) {
        User localUser = userRepository.findByUsername(user.getUsername());

        if (localUser != null) {
            log.info("User with username {} already exist. Nothing will be done. ", user.getUsername());
        } else {
            String encryptedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encryptedPassword);

            for (UserRole ur : userRoles) {
                roleDao.save(ur.getRole());
            }

            user.getUserRoles().addAll(userRoles);

//            user.setPrimaryAccount(accountService.createPrimaryAccount());
//            user.setSavingsAccount(accountService.createSavingsAccount());

            localUser = userRepository.save(user);
        }

        return localUser;
    }

    public int migrationDWtoRAW(String centerid) {
        int rowcount = 0;
        int count = deleteRawUserTbl(centerid);


        System.out.println("inside add Yo User");


        try {

            Query query = entityManager.createNativeQuery(" INSERT INTO raw_user SELECT * FROM dw_user ");

            rowcount = query.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in migrationDWtoRAW " + e.getMessage());
        }


        return rowcount;
    }

    public int deleteRawUserTbl(String centerid) {
        int rowcount = 0;
        try {
            Query query0 = entityManager.createNativeQuery(" delete from raw_user where source_centerid = ? ");
            query0.setParameter(1, centerid);
            rowcount = query0.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in deleteYoUserTbl " + e.getMessage());
        }

        return rowcount;
    }


    public RawUser getRawUserDetails(String username) {

        RawUser rawUser = null;
        System.out.println("usenae " + username);
        try {
            Query query = entityManager.createNativeQuery(
                    " select * from " +
                            " raw_user where mobilenumber = ? ", RawUser.class);

            query.setParameter(1, username);

            rawUser = (RawUser) query.getSingleResult();

        } catch (NoResultException nre) {

            return rawUser;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in checkIfUserExistsinRaw " + e.getMessage());
        }
        return rawUser;

    }


    public Long getServiceId(String center_id, String service_category_id) {
        System.out.println("center_id = [" + center_id + "], service_category_id = [" + service_category_id + "]");

        java.math.BigInteger service_id = null;
        try {
            Query query = entityManager.createNativeQuery(" select s.id as service_id from " +
                    "service s, " +
                    "service_category sc " +
                    "where " +
                    "s.service_category_id = sc.id and " +
                    "s.center_id = ? and " +
                    "sc.id = ? ");


            query.setParameter(1, Long.valueOf(center_id));
            query.setParameter(2, Long.valueOf(service_category_id));
            service_id = (java.math.BigInteger) query.getSingleResult();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in getServiceId " + e.getMessage());
        }

        return service_id.longValue();

    }


}


//select * from
//        member_services_pymt mps,
//        member_services ms
//        where
//        ms.id = mps.msid and
//        ms.userid = 3

