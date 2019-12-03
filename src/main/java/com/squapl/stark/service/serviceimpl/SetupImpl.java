package com.squapl.stark.service.serviceimpl;

import com.fasterxml.jackson.databind.JsonNode;
import com.squapl.stark.Exception.EntityAlreadyPresentException;
import com.squapl.stark.Exception.EntityNotFoundException;
import com.squapl.stark.model.*;
import com.squapl.stark.model.security.UserRole;
import com.squapl.stark.repository.*;
import com.squapl.stark.service.SetupService;
import com.squapl.stark.service.UserService;
import com.squapl.stark.util.APIResponseObj;
import com.squapl.stark.util.CustomHelper;
import com.squapl.stark.util.Helper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
@Transactional
@Slf4j
public class SetupImpl implements SetupService {

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

    @Autowired
    private CustomHelper customHelper;

//    Corporates - addCorporates, getAllCorporates, getCorporatesCount, updateCorporate,

    public APIResponseObj addCorporates(Corporate corporateVO) {

        JsonNode detailsNode = corporateVO.getDetails();
        String phonenumber = detailsNode.path("contact").get("phone").asText();

        Users tmpUser = userRepository.findByMobilenumber(phonenumber);

        if (tmpUser != null) {
            throw new EntityAlreadyPresentException("Phone");
        }

        Corporate d_corporate = corporateRepository.findByName(corporateVO.getName());

        if (d_corporate != null) {
            throw new EntityAlreadyPresentException("Corporate");
        } else {
            Corporate tmpCorporate = corporateRepository.save(corporateVO);

            if (tmpCorporate != null) {
                Users user = createUser(phonenumber, corporateVO.getName(), phonenumber, customHelper.getLoggedInUserName(), "CORPORATE", tmpCorporate, null);
            }

        }

        return new APIResponseObj("SUCCESS", "", "");
    }

    public List<Corporate> getAllCorporates(String status) {
        List<Corporate> corporateList = corporateRepository.findByIsactiveOrderByNameAsc(status);
        return corporateList;
    }

    public int getCorporatesCount(String status) {
        Long rowcount = corporateRepository.countByIsactive(status);
        return rowcount.intValue();
    }

    public APIResponseObj updateCorporate(Corporate corporateVO) {

        Corporate returnCorporate = corporateRepository.save(corporateVO);
        return new APIResponseObj("SUCCESS", "", "");

    }

    // Centers - addCenter, getAllCenters, getCentersCount, updateCenter
    public APIResponseObj addCenter(Center centerVO) {

        JsonNode detailsNode = centerVO.getDetails();
        String phonenumber = detailsNode.path("contact").get("phone").asText();

        Users tmpUser = userRepository.findByMobilenumber(phonenumber);

        if (tmpUser != null) {
            throw new EntityAlreadyPresentException("Phone");
        }

        Center d_center = centerRepository.findByName(centerVO.getName());

        if (d_center != null) {
            throw new EntityAlreadyPresentException("Center");
        } else {
            Center tmpCenter = centerRepository.save(centerVO);

            if (tmpCenter != null) {
                Users user = createUser(phonenumber, centerVO.getName(), phonenumber, String.valueOf(customHelper.getLoggedInUserName()), "CENTER_ADMIN", null, tmpCenter);
            }

        }

        return new APIResponseObj("SUCCESS", "", "");
    }

    public List<Center> getAllCenters(String status, Long corporate_id) {
        Corporate corporate = corporateRepository.findById(corporate_id)
                .orElseThrow(() -> new EntityNotFoundException("Corporate {" + corporate_id + "}"));

        List<Center> centerListList = centerRepository.findByIsactiveAndCorporateOrderByNameAsc(status, corporate);
        return centerListList;

    }


    public int getCentersCount(String status, Long corporate_id) {

        Corporate corporate = corporateRepository.findById(corporate_id)
                .orElseThrow(() -> new EntityNotFoundException("Corporate {" + corporate_id + "}"));

        Long rowCount = centerRepository.countByIsactiveAndCorporate(status, corporate);
        System.out.println("row cound >> " + rowCount);

        return rowCount.intValue();
    }


    public APIResponseObj updateCenter(Center centerVO) {

        Center returnCorporate = centerRepository.save(centerVO);
        return new APIResponseObj("SUCCESS", "", "");

    }


    // trainser - addTrainer,

    public TrainerDetails addTrainer(TrainerDetails trainerDetailsVO) {

        Users user = trainerDetailsVO.getTrainuser();
        user.setPassword("111111");


        Set<UserRole> userRoles = new HashSet<>();
        userRoles.add(new UserRole(user, roleDao.findByName("TRAINER")));
        System.out.println("user roles >> " + userRoles.toString());

        Users trainUser = userService.createUser(user, userRoles);

        trainerDetailsVO.setTrainuser(trainUser);

        TrainerDetails newTrainerDetails = trainerDetailsRepository.save(trainerDetailsVO);

        return newTrainerDetails;
    }


    // trainser - editTrainer,
    public TrainerDetails editTrainer(TrainerDetails trainerDetailsVO) {

        userRepository.updateTrainerInfo(trainerDetailsVO);

        TrainerDetails newTrainerDetails = trainerDetailsRepository.save(trainerDetailsVO);

        return newTrainerDetails;
    }

    public Users addMc(Users userVO) {
        userVO.setPassword("111111");
        Set<UserRole> userRoles = new HashSet<>();
        userRoles.add(new UserRole(userVO, roleDao.findByName("MEMBER_COORDINATOR")));
        System.out.println("user roles >> " + userRoles.toString());

        return (Users) userService.createUser(userVO, userRoles);
    }


    public int editMc(Users userVO) {
        return userRepository.updateMcInfo(userVO);

    }

    public Users addCA(Users userVO) {
        userVO.setPassword("111111");
        Set<UserRole> userRoles = new HashSet<>();
        userRoles.add(new UserRole(userVO, roleDao.findByName("CENTER_ADMIN")));
        System.out.println("user roles >> " + userRoles.toString());

        return (Users) userService.createUser(userVO, userRoles);
    }


    public int editCA(Users userVO) {
        return userRepository.updateCAInfo(userVO);

    }


    // service category

    public APIResponseObj addServiceCategory(ServiceCategory serviceCategoryVO) {

        if (serviceCategoryVO.getSelectedsubcatids().length() > 0) {
            List<String> list = Stream.of(serviceCategoryVO.getSelectedsubcatids().split(","))
                    .collect(Collectors.toList());
            Set<CategorySubCategory> categorySubCategories = new HashSet<>();

            list.forEach(e -> {

                ServiceSubCategory tempSSC = serviceSubCategoryRepository.findById(Long.valueOf(e))
                        .orElseThrow(() -> new EntityNotFoundException("Corporate {" + Long.valueOf(e + "}")));
                categorySubCategories.add(new CategorySubCategory(serviceCategoryVO, tempSSC));
            });

            serviceCategoryVO.getCategorySubCategories().addAll(categorySubCategories);
        }


        ServiceCategory serviceCategory = serviceCategoryRepository.save(serviceCategoryVO);

        return new APIResponseObj("SUCCESS", "", "");

    }

    public APIResponseObj updateServiceCategory(ServiceCategory serviceCategoryVO) {
        if (serviceCategoryVO.getSelectedsubcatids().length() > 0) {
            List<String> list;
            list = Stream.of(serviceCategoryVO.getSelectedsubcatids().split(","))
                    .collect(Collectors.toList());

            Set<CategorySubCategory> categorySubCategories;
            categorySubCategories = new HashSet<>();

            list.forEach(e -> {

                ServiceSubCategory tempSSC = serviceSubCategoryRepository.findById(Long.valueOf(e))
                        .orElseThrow(() -> new EntityNotFoundException("Corporate {" + Long.valueOf(e + "}")));
                categorySubCategories.add(new CategorySubCategory(serviceCategoryVO, tempSSC));
            });

            serviceCategoryVO.getCategorySubCategories().addAll(categorySubCategories);
        }

        ServiceCategory sc = new ServiceCategory();
        sc.setId(serviceCategoryVO.getId());

        categorySubCategoryRepository.deleteByServiceCategory(sc);
        ServiceCategory returnserviceCategory = serviceCategoryRepository.save(serviceCategoryVO);
        return new APIResponseObj("SUCCESS", "", "");

    }


    public List<ServiceCategory> getAllServiceCategories(String status, Long center_id) {
        Center center = centerRepository.findById(center_id).
                orElseThrow(() -> new EntityNotFoundException(" Center {" + center_id + "}"));
        List<ServiceCategory> serviceCategoryList = serviceCategoryRepository.findAllByIsactiveAndCenter(status, center);
        return serviceCategoryList;
    }


    public List<ServiceSubCategory> getServiceSubCategory(Long category_id) {
        ServiceCategory serviceCategory = serviceCategoryRepository.findById(category_id).
                orElseThrow(() -> new EntityNotFoundException(" category_id {" + category_id + "}"));

        Set<CategorySubCategory> subset = serviceCategory.getCategorySubCategories();
        //   List<CategorySubCategory> aList = subset.stream().collect(Collectors.toList());


        // DND - two ways to access streams 1. lambda 2. method reference
        // (1)
        List<ServiceSubCategory> aList = subset.stream()
                .map(e -> e.getServiceSubCategory())
                .collect(Collectors.toList());

        // (2)
//        List<ServiceSubCategory> aList1 = subset.stream()
//                .map(CategorySubCategory::getServiceSubCategory)
//                .collect(Collectors.toList());

        System.out.println(" wil it work " + aList.toString());


        return aList;
    }

    // sscategory

    public APIResponseObj addServiceSubCategory(ServiceSubCategory serviceSubCategoryVO) {

        //   System.out.println("test>> " + serviceSubCategoryVO.toString());
        serviceSubCategoryRepository.save(serviceSubCategoryVO);

        return new APIResponseObj("SUCCESS", "", "");

    }

    public List<ServiceSubCategory> getAllServiceSubCategories(String status, Long center_id) {
        Center center = centerRepository.findById(center_id).
                orElseThrow(() -> new EntityNotFoundException(" Center {" + center_id + "}"));
        List<ServiceSubCategory> serviceSubCategoryList = serviceSubCategoryRepository.findAllByIsactiveAndCenter(status, center);
        return serviceSubCategoryList;
    }

    public APIResponseObj updateSSCategory(ServiceSubCategory serviceSubCategory) {

        ServiceSubCategory returnserviceSubCategory = serviceSubCategoryRepository.save(serviceSubCategory);
        return new APIResponseObj("SUCCESS", "", "");

    }


    // common - updateEntityStatus, createUser
    public APIResponseObj updateEntityStatus(String entity, Long id, String status, Long loggedinuserid, Date whenupdate) {

        if (entity.equalsIgnoreCase("servicesubcategory")) {
            ServiceSubCategory serviceSubCategory = serviceSubCategoryRepository.getOne(id);
            serviceSubCategory.setIsactive(status);
            serviceSubCategoryRepository.save(serviceSubCategory);
        } else if (entity.equalsIgnoreCase("servicecategory")) {
            ServiceCategory serviceCategory = serviceCategoryRepository.getOne(id);
            serviceCategory.setIsactive(status);
            serviceCategoryRepository.save(serviceCategory);
        } else if (entity.equalsIgnoreCase("corporate")) {
            corporateRepository.setStatusForCorporate(id, status, loggedinuserid, whenupdate);
        } else if (entity.equalsIgnoreCase("center")) {
            centerRepository.setStatusForCenter(id, status, customHelper.getLoggedInUserName(), whenupdate);
        }


        return new APIResponseObj("SUCCESS", "", "");
    }

    @Transactional
    public Users createUser(String username, String firstname, String phonenumber, String loggedinuserid, String role, Corporate corporate, Center center) {
        String token = "";
        Users user = null;

        System.out.println("22222222");
        user = new Users();
        user.setUsername(username);
        user.setPassword("111111");
        user.setMobilenumber(phonenumber);
        user.setVerified("N");
        user.setStatus("A");
        //   user.setCreatedby(Long.valueOf(loggedinuserid));
        //   user.setCreateddatetime(new Date());
        user.setFirstname(firstname);
        user.setCorporate(corporate);
        user.setCenter(center);

        Set<UserRole> userRoles = new HashSet<>();
        userRoles.add(new UserRole(user, roleDao.findByName(role)));

        Users newUser = userService.createUser(user, userRoles);


        return newUser;
    }


    public APIResponseObj addServices(Services servicesVO) {
        servicesRepository.save(servicesVO);
        return new APIResponseObj("SUCCESS", "", "");
    }
}

