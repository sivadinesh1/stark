package com.squapl.stark.service.serviceimpl;

import com.squapl.stark.Exception.EntityNotFoundException;
import com.squapl.stark.model.Center;
import com.squapl.stark.model.Services;
import com.squapl.stark.repository.CenterRepository;
import com.squapl.stark.repository.RoleDao;
import com.squapl.stark.repository.ServicesRepository;
import com.squapl.stark.repository.UserRepository;
import com.squapl.stark.service.ServiceMgmtService;
import com.squapl.stark.util.Helper;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;


@Service
@Transactional
@Slf4j
public class ServiceMgmtImpl implements ServiceMgmtService {
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

    @Autowired
    private ServicesRepository servicesRepository;

    @Autowired
    private CenterRepository centerRepository;

    public List<Services> getAllServices(String status, String centerid) {
        Center center = centerRepository.findById(Long.valueOf(centerid)).
                orElseThrow(() -> new EntityNotFoundException(" Center {" + centerid + "}"));
        JSONArray resultJArr = null;
        List<Services> service = servicesRepository.findAllByIsactiveAndCenter(status, center);

        return service;
    }

    public JSONArray getServices(String status, String centerid, String service_category_id) {
        JSONArray resultJArr = servicesRepository.getServices(status, centerid, service_category_id);


        return resultJArr;
    }


    public JSONArray getAllServices(String centerid, String categoryid, String subcategoryid) {

        JSONArray resultJArr = null;

        try {
            Query query = entityManager.createNativeQuery(" select cast(array_to_json(array_agg(row_to_json(t))) as text) " +
                    " from ( " +

                    " select " +
                    " c.id as center_id, " +
                    " c.name as center_name, " +
                    " s.id as service_id, " +
                    " s.name as service_name, " +
                    " sd.sessions as sessions, " +
                    " sd.base_grossfee as base_grossfee, " +
                    " sd.base_tax as base_tax, " +
                    " sd.base_nettotal as base_nettotal, " +
                    " s.description as service_description, " +
                    " sc.id as service_category_id, " +
                    " sc.name as service_category_name, " +
                    " sc.description as service_categoru_description, " +
                    " ssc.id as service_sub_category_id, " +
                    " ssc.name as service_sub_categor_name, " +
                    " ssc.description as service_sub_category_dscription " +
                    " from " +
                    " service s, " +
                    " service_sub_category ssc, " +
                    " service_category sc, " +
                    " service_details sd, " +
                    " center_details c " +
                    " where " +
                    " sc.id = s.service_category_id and " +
                    " ssc.id = s.service_sub_category_id and " +
                    " c.id = s.center_id and " +
                    " sd.id = s.id and " +
                    " ssc.isactive = 'Y' and " +
                    " sc.isactive = 'Y' and " +
                    " s.isactive = 'Y' and " +
                    " s.center_id = " + centerid + " and " +
                    " sc.id = " + categoryid + " and " +
                    " ssc.id = " + subcategoryid +
                    " order by " +
                    " sd.sessions asc " +


                    " ) t ");

            resultJArr = helper.getJSONArray((String) (query.getResultList().get(0)));


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in getTrainers " + e.getMessage());
        }


        return resultJArr;
    }


    public JSONArray getTrainers(String centerid) {

        JSONArray resultJArr = null;

        try {
            Query query = entityManager.createNativeQuery(" select cast(array_to_json(array_agg(row_to_json(t))) as text) " +
                    " from ( select u.id as id, " +
                    " u.firstname as first_name, " +
                    " u.lastname as last_name, " +
                    " u.center_id as center_id, " +
                    " u.mobilenumber as mobile_number, " +
                    " u.profileimgurl as profile_image, " +
                    " u.status as status, " +
                    " u.dob as dob, " +
                    " td.level as category, " +
                    " td.trainerfee as trainer_fee " +
                    " from " +
                    " users u, " +
                    " user_role ur, " +
                    " trainer_details td " +
                    " where " +
                    " u.id = ur.user_id and " +
                    " td.userid = u.id and " +
                    " ur.role_id in (4) and " +
                    " u.center_id = " + centerid + " ) t ");

            resultJArr = helper.getJSONArray((String) (query.getResultList().get(0)));
            System.out.println("increment >> " + resultJArr);

        } catch (Exception e) {
            System.out.println("Error in getTrainers " + e.getMessage());
        }


        return resultJArr;

    }

    public JSONArray getPackages(String centerid) {
        System.out.println("centerid ..." + centerid);
        JSONArray resultJArr = null;
        try {

            Query query = entityManager.createNativeQuery(" select cast(array_to_json(array_agg(row_to_json(t))) as text) " +
                    " from ( " +

                    " select s.id service_id, " +
                    " s.name as service_name, " +
                    " s.description as service_description, " +
                    " sc.id as service_category_id, " +
                    " sc.name as service_category, " +
                    " ssc.id as service_sub_category_id, " +
                    " ssc.name as service_sub_category_name, " +
                    " s.sessions as sessions, " +
                    " s.validity as validity, " +
                    " s.graceperiod as grace_period, " +
                    " s.base_grossfee as base_gross_fee, " +
                    " s.base_nettotal as base_net_total, " +
                    " s.base_tax as base_tax " +
                    " from " +

                    " service s, " +
                    " service_category sc, " +
                    " service_sub_category ssc " +
                    " where " +

                    " s.service_category_id = sc.id and " +
                    " s.service_sub_category_id = ssc.id and " +
                    " s.center_id = " + centerid + " ) t ");


            resultJArr = helper.getJSONArray((String) (query.getResultList().get(0)));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in getPackages.." + e.getMessage());
        }

        return resultJArr;

    }


    /**
     * Status - R : REGISTERED
     *
     * @param center_id
     * @param e_user_id
     * @param service_id
     * @return
     */
    public int insertUserServicesSelfEnquiry(String center_id, String e_user_id, Long service_id) {
        System.out.println("inside add Yo User");
        int rowcount = 0;

        try {

            Query query = entityManager.createNativeQuery(" insert into enquiry (center_id, e_user_id, user_type, " +
                    "service_id, status, trial_status, enquiry_datetime, lead_source)  " +

                    " values (?, ?, ?, ?, ?, ?, ?, ?) ");

            query.setParameter(1, Long.valueOf(center_id));
            query.setParameter(2, Long.valueOf(e_user_id));
            query.setParameter(3, "EXISTING_USER");
            query.setParameter(4, service_id);
            query.setParameter(5, "R");
            query.setParameter(6, "NO_TRIAL");
            query.setParameter(7, new Date());
            query.setParameter(8, "MEMBER_SELF");

            rowcount = query.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in insertUserServicesSelfEnquiry " + e.getMessage());
        }

        return rowcount;
    }


    public int checkExistingEnquiries(String center_id, String e_user_id, Long service_id) {

        java.math.BigInteger count = new BigInteger("0");

        try {

            Query query = entityManager.createNativeQuery(
                    " select count(*) from enquiry where " +
                            "  center_id = " + center_id + " and " +
                            " e_user_id = " + e_user_id + " and " +
                            " status = 'R' and service_id = " + service_id);

            count = (java.math.BigInteger) query.getSingleResult();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in checkExistingEnquiries.." + e.getMessage());
        }

        return count.intValue();

    }

    public JSONArray getServiceSubCatByCat(String center_id, String category_id) {

        StringBuilder sb = new StringBuilder();
        sb.append("select cast(json_agg(t) as text)");
        sb.append(" from ( ");
        sb.append(" select ssc.id as service_sub_category_id, ssc.name as service_sub_category, sc.id as service_category_id, sc.name as category ");
        sb.append(" from ");
        sb.append(" category_sub_category csc, ");
        sb.append(" service_sub_category ssc, ");
        sb.append(" service_category sc ");
        sb.append(" where ");
        sb.append(" ssc.id = csc.sub_category_id and ");
        sb.append(" sc.id = csc.category_id and ");
        sb.append(" csc.category_id = ? and  ");
        sb.append(" sc.center_id = ? ");
        sb.append("  ) t  ");

        Query query = entityManager.createNativeQuery(sb.toString());

        query.setParameter(1, Long.valueOf(category_id));
        query.setParameter(2, Long.valueOf(center_id));

        String returnStr = (String) query.getSingleResult();
        return helper.getJSONArray(returnStr);


    }


}






