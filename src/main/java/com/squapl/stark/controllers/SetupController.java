package com.squapl.stark.controllers;

import com.squapl.stark.aop.LogExecutionTime;
import com.squapl.stark.model.*;
import com.squapl.stark.model.security.UserRole;
import com.squapl.stark.repository.CategorySubCategoryRepository;
import com.squapl.stark.repository.RoleDao;
import com.squapl.stark.repository.TrainerDetailsRepository;
import com.squapl.stark.repository.UserDao;
import com.squapl.stark.service.DwUtilService;
import com.squapl.stark.service.SetupService;
import com.squapl.stark.service.UserService;
import com.squapl.stark.util.APIResponseObj;
import com.squapl.stark.util.Helper;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/api")
public class SetupController {

    @Autowired
    DwUtilService dwUtilService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private SetupService setupService;

    @Autowired
    private Helper helper;

    @Autowired
    TrainerDetailsRepository trainerDetailsRepository;

    @LogExecutionTime
    @RequestMapping(
            value = "/getallcorporates/{status}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAllCorporates(@PathVariable("status") String status) {
        System.out.println(">>>>> " + status);
        List<Corporate> corporateList = setupService.getAllCorporates(status);

        return new ResponseEntity<>(new APIResponseObj("SUCCESS", "", corporateList), HttpStatus.OK);
    }

    @RequestMapping(
            value = "/getallcenters/{status}/{corporate_id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAllCenters(@PathVariable("status") String status, @PathVariable("corporate_id") String corporate_id) {

        List<Center> restultJArr = setupService.getAllCenters(status, Long.valueOf(corporate_id));

        return new ResponseEntity<>(new APIResponseObj("SUCCESS", "", restultJArr), HttpStatus.OK);
    }

    @RequestMapping(
            value = "/getcorporatescount/{status}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getCorporatesCount(@PathVariable("status") String status) {
        int rowcount = setupService.getCorporatesCount(status);

        return new ResponseEntity<>(new APIResponseObj("SUCCESS", String.valueOf(rowcount), ""), HttpStatus.OK);
    }

    @RequestMapping(
            value = "/getcenterscount/{status}/{corporateid}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getCentersCount(@PathVariable("status") String status, @PathVariable("corporateid") Long corporate_id) {
        int rowcount = setupService.getCentersCount(status, corporate_id);

        return new ResponseEntity<>(new APIResponseObj("SUCCESS", String.valueOf(rowcount), ""), HttpStatus.OK);
    }


    @RequestMapping(value = "/corporate", method = RequestMethod.POST)
    public ResponseEntity<?> addCorporate(@RequestBody Corporate corporateVO) {
        String token = "";
        APIResponseObj apiResponseObj = setupService.addCorporates(corporateVO);
        return new ResponseEntity<>(apiResponseObj, HttpStatus.OK);
    }


    @RequestMapping(value = "/update-corporate", method = RequestMethod.POST)

    public ResponseEntity<?> updateCorporate(@RequestBody Corporate corporateVO) throws Exception {
        String token = "";

        APIResponseObj apiResponseObj = setupService.updateCorporate(corporateVO);
        return new ResponseEntity<>(apiResponseObj, HttpStatus.OK);

    }


    @RequestMapping(value = "/update-center", method = RequestMethod.POST)

    public ResponseEntity<?> updateCenter(@RequestBody Center centerVO) throws Exception {
        String token = "";
        System.out.println("update center " + centerVO.toString());
        APIResponseObj apiResponseObj = setupService.updateCenter(centerVO);
        return new ResponseEntity<>(apiResponseObj, HttpStatus.OK);


    }

    @RequestMapping(value = "/center", method = RequestMethod.POST)
    public ResponseEntity<?> addCenter(@RequestBody Center centerVO) {
        String token = "";

        APIResponseObj apiResponseObj = setupService.addCenter(centerVO);
        return new ResponseEntity<>(apiResponseObj, HttpStatus.OK);

    }



    @Transactional
    @RequestMapping(value = "/add-trainer", method = RequestMethod.POST)
    public ResponseEntity<?> addTrainer(@RequestBody TrainerDetails trainerDetailsVO) {

        System.out.println("print trainer details " + trainerDetailsVO.toString());

        setupService.addTrainer(trainerDetailsVO);



//        String token = "";
//        JSONObject result = helper.getJsonObj(stringToParse);
//        log.debug("PARAMS >> " + result.toJSONString());
//        String phonenumber = "";
//
//            log.debug("PARAMS NEW >> " + result.toJSONString());

//            String firstname = (String) result.get("firstname");
//            String phone = (String) result.get("phone");
//            String email = (String) result.get("email");
//            Long center_id = (Long) result.get("center_id");
//            String gender = (String) result.get("gender");
//            String dob = (String) result.get("dob");
//
//            String level = (String) result.get("level");
//            String trainerfee = (String) result.get("trainerfee");
//
//            Long loggedinuserid = (Long) result.get("loggedinuserid");
//
//            System.out.println("json loggedinuserid obj.." + loggedinuserid);


//            User isDuplicatePhone = userService.getUserDetails(phonenumber);
//
//            if (isDuplicatePhone != null) {
//                return new ResponseEntity<>(new APIResponseObj("FAILURE", "DUPLICATE_PHONE", ""), HttpStatus.OK);
//            }
//
//            User user = new User();
//            Center center = new Center();
//            center.setId(Long.valueOf(center_id));
//
//            user.setCenter(center);
//            user.setFirstname(firstname);
//            user.setStatus("A");
//            user.setVerified("N");
//            user.setMobilenumber(phone);
//            user.setUsername(phone);
//            user.setEmail(email);
//            user.setCreatedby(loggedinuserid);
//            user.setCreateddatetime(new Date());
//            user.setCorporate(null);
//            user.setSignup_mode("");
//            user.setGender(gender);
//            user.setDob(dob);
//
//            System.out.println("user >> @@@ ");
//
//            user.setPassword("111111");
//
//
//            Set<UserRole> userRoles = new HashSet<>();
//            userRoles.add(new UserRole(user, roleDao.findByName("TRAINER")));
//            System.out.println("user roles >> " + userRoles.toString());
//
//            User newUser = userService.createUser(user, userRoles);
//
//            if (newUser != null) {
//                TrainerDetails trainerDetails = new TrainerDetails();
//                trainerDetails.setLevel(level);
//                trainerDetails.setTrainerfee(Integer.valueOf(trainerfee));
//                trainerDetails.setTrainuser(newUser);
//                trainerDetails.setCreatedby(loggedinuserid);
//                trainerDetails.setCreateddatetime(new Date());
//
//                setupService.addTrainer(trainerDetails);
//            }


            System.out.println("user >> @@@ ### ");




        return new ResponseEntity<>(new APIResponseObj("SUCCESS", "", ""), HttpStatus.OK);


    }


//    @Transactional
//    @RequestMapping(value = "/add-trainer", method = RequestMethod.POST)
//    public ResponseEntity<?> addTrainer(@RequestBody String stringToParse) throws Exception {
//        String token = "";
//        JSONObject result = helper.getJsonObj(stringToParse);
//        log.debug("PARAMS >> " + result.toJSONString());
//        String phonenumber = "";
//
//        try {
//            log.debug("PARAMS NEW >> " + result.toJSONString());
//
//            String firstname = (String) result.get("firstname");
//            String phone = (String) result.get("phone");
//            String email = (String) result.get("email");
//            Long center_id = (Long) result.get("center_id");
//            String gender = (String) result.get("gender");
//            String dob = (String) result.get("dob");
//
//            String level = (String) result.get("level");
//            String trainerfee = (String) result.get("trainerfee");
//
//            Long loggedinuserid = (Long) result.get("loggedinuserid");
//
//            System.out.println("json loggedinuserid obj.." + loggedinuserid);
//
//
//            User isDuplicatePhone = userService.getUserDetails(phonenumber);
//
//            if (isDuplicatePhone != null) {
//                return new ResponseEntity<>(new APIResponseObj("FAILURE", "DUPLICATE_PHONE", ""), HttpStatus.OK);
//            }
//
//            User user = new User();
//            Center center = new Center();
//            center.setId(Long.valueOf(center_id));
//
//            user.setCenter(center);
//            user.setFirstname(firstname);
//            user.setStatus("A");
//            user.setVerified("N");
//            user.setMobilenumber(phone);
//            user.setUsername(phone);
//            user.setEmail(email);
//            user.setCreatedby(loggedinuserid);
//            user.setCreateddatetime(new Date());
//            user.setCorporate(null);
//            user.setSignup_mode("");
//            user.setGender(gender);
//            user.setDob(dob);
//
//            System.out.println("user >> @@@ ");
//
//            user.setPassword("111111");
//
//
//            Set<UserRole> userRoles = new HashSet<>();
//            userRoles.add(new UserRole(user, roleDao.findByName("TRAINER")));
//            System.out.println("user roles >> " + userRoles.toString());
//
//            User newUser = userService.createUser(user, userRoles);
//
//            if (newUser != null) {
//                TrainerDetails trainerDetails = new TrainerDetails();
//                trainerDetails.setLevel(level);
//                trainerDetails.setTrainerfee(Integer.valueOf(trainerfee));
//                trainerDetails.setTrainuser(newUser);
//                trainerDetails.setCreatedby(loggedinuserid);
//                trainerDetails.setCreateddatetime(new Date());
//
//                setupService.addTrainer(trainerDetails);
//            }
//
//
//            System.out.println("user >> @@@ ### ");
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println(".........");
//            return new ResponseEntity<>(new APIResponseObj("FAILURE", "SERVER_ERROR", ""), HttpStatus.OK);
//
//
//        }
//
//        return new ResponseEntity<>(new APIResponseObj("SUCCESS", new JwtResponse(token).getToken(), ""), HttpStatus.OK);
//
//
//    }

    // service category

    @RequestMapping(
            value = "/getallservicecategories/{status}/{center_id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAllServiceCategories(@PathVariable("status") String status,
                                                  @PathVariable("center_id") String center_id) {
        List<ServiceCategory> serviceCategoryList = this.setupService.getAllServiceCategories(status, Long.valueOf(center_id));


//        serviceCategoryList.forEach(element -> {
//            Set<CategorySubCategory> tt = element.getCategorySubCategories();
//            System.out.println("wtf " + tt.toString());
//        });


        return new ResponseEntity<>(new APIResponseObj("SUCCESS", "", serviceCategoryList), HttpStatus.OK);
    }


    @RequestMapping(
            value = "/getservicesubcategory/{category_id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getServiceSubCategory(@PathVariable("category_id") String category_id) {
        System.out.println("print " + category_id);
        List<ServiceSubCategory> test = this.setupService.getServiceSubCategory(Long.valueOf(category_id));


        //  List<ServiceCategory> serviceCategoryList = this.setupService.getServiceCategory(Long.valueOf(id));

        //  System.out.println("wtf " + serviceCategoryList.toString());


        return new ResponseEntity<>(new APIResponseObj("SUCCESS", "", test), HttpStatus.OK);
    }

    @RequestMapping(
            value = "/getservicecategory/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getservicecategory(@PathVariable("id") String id) {
        //  List<ServiceCategory> serviceCategoryList = this.setupService.getServiceCategory(Long.valueOf(id));

        //  System.out.println("wtf " + serviceCategoryList.toString());


        return new ResponseEntity<>(new APIResponseObj("SUCCESS", "", ""), HttpStatus.OK);
    }

    @RequestMapping(value = "/service-category", method = RequestMethod.POST)
    public ResponseEntity<?> addServiceCategory(@RequestBody ServiceCategory serviceCategoryVO) {
        String token = "";
        System.out.println("KLKLKL " + serviceCategoryVO.toString());
        setupService.addServiceCategory(serviceCategoryVO);

        return new ResponseEntity<>(new APIResponseObj("SUCCESS", new JwtResponse(token).getToken(), ""), HttpStatus.OK);


    }


    @RequestMapping(value = "/servicecategory", method = RequestMethod.PUT)
    public ResponseEntity<?> updateServiceCategory(@RequestBody ServiceCategory serviceCategoryVO) {
        System.out.println("check " + serviceCategoryVO.toString());
        APIResponseObj apiResponseObj = setupService.updateServiceCategory(serviceCategoryVO);
        return new ResponseEntity<>(apiResponseObj, HttpStatus.OK);
    }


    // ss category

    @RequestMapping(
            value = "/getallservicesubcategories/{status}/{center_id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAllServiceSubCategories(@PathVariable("status") String status,
                                                     @PathVariable("center_id") String center_id) {
        System.out.println("test.. " + status);
        System.out.println("test.. " + center_id);
        List<ServiceSubCategory> serviceSubCategoryList = this.setupService.getAllServiceSubCategories(status, Long.valueOf(center_id));
        System.out.println("test.. " + serviceSubCategoryList.toString());
        return new ResponseEntity<>(new APIResponseObj("SUCCESS", "", serviceSubCategoryList), HttpStatus.OK);
    }


    @RequestMapping(value = "/service-sub-category", method = RequestMethod.POST)
    public ResponseEntity<?> addServiceSubCategory(@RequestBody ServiceSubCategory serviceSubCategoryVO) {
        String token = "";

        setupService.addServiceSubCategory(serviceSubCategoryVO);

        return new ResponseEntity<>(new APIResponseObj("SUCCESS", new JwtResponse(token).getToken(), ""), HttpStatus.OK);


    }


    @RequestMapping(value = "/sscategory", method = RequestMethod.PUT)
    public ResponseEntity<?> updateSSCategory(@RequestBody ServiceSubCategory serviceSubCategoryVO) {
        System.out.println("check " + serviceSubCategoryVO.toString());
        APIResponseObj apiResponseObj = setupService.updateSSCategory(serviceSubCategoryVO);
        return new ResponseEntity<>(apiResponseObj, HttpStatus.OK);
    }


    @RequestMapping(value = "/entity-status/{entity}/{id}/{status}/{loggedinuserid}/{whenupdated}", method = RequestMethod.PUT)

    public ResponseEntity<?> updateEntityStatus(@PathVariable("entity") String entity, @PathVariable("id") String
            id, @PathVariable("status") String status, @PathVariable("loggedinuserid") Long loggedinuserid,
                                                @PathVariable("whenupdated") String whenupdated) throws Exception {
        String token = "";


        APIResponseObj apiResponseObj = setupService.updateEntityStatus(entity, Long.valueOf(id),
                status, loggedinuserid, new Date(whenupdated));


        return new ResponseEntity(apiResponseObj, HttpStatus.OK);


    }


}

