package com.squapl.stark.service.serviceimpl;

import com.squapl.stark.repository.*;
import com.squapl.stark.service.EnquiryService;
import com.squapl.stark.service.UserService;
import com.squapl.stark.util.Helper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Service
@Transactional
@Slf4j
public class EnquiryImpl implements EnquiryService {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    Helper helper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CorporateRepository corporateRepository;

    @Autowired
    CenterRepository centerRepository;

    @Autowired
    UserService userService;

    @Autowired
    RoleDao roleDao;

    @Autowired
    ServiceSubCategoryRepository serviceSubCategoryRepository;

    @Autowired
    ServiceCategoryRepository serviceCategoryRepository;

    @Autowired
    private CategorySubCategoryRepository categorySubCategoryRepository;
    @Autowired
    private TrainerDetailsRepository trainerDetailsRepository;
    @Autowired
    private ServicesRepository servicesRepository;

//    public JSONArray getServices(String status, String center_id, String service_category_id) {
//
//        JSONArray resultJArr = null;
//        try {
//
//            StringBuilder sb = new StringBuilder();
//            sb.append(" select cast(array_to_json(array_agg(row_to_json(t))) as text ");
//            sb.append(" from ( ");
//            sb.append(" select * from service where center_id = ? and service_category_id = ? ");
//            sb.append(") t ");
//
//            Query query = entityManager.createNativeQuery(sb.toString(), Services.class);
//
//
//            query.setParameter(1, Long.valueOf(center_id));
//            query.setParameter(2, Long.valueOf(service_category_id));
//
//
//            resultJArr = helper.getJSONArray((String) (query.getResultList().get(0)));
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("Error in getPackages.." + e.getMessage());
//        }
//
//        return resultJArr;
//
//    }

}

