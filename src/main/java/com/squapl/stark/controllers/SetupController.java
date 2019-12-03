package com.squapl.stark.controllers;

import com.squapl.stark.aop.LogExecutionTime;
import com.squapl.stark.model.*;
import com.squapl.stark.repository.RoleDao;
import com.squapl.stark.repository.TrainerDetailsRepository;
import com.squapl.stark.repository.UserRepository;
import com.squapl.stark.service.DwUtilService;
import com.squapl.stark.service.SetupService;
import com.squapl.stark.service.UserService;
import com.squapl.stark.util.APIResponseObj;
import com.squapl.stark.util.Helper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class SetupController {

    @Autowired
    DwUtilService dwUtilService;
    @Autowired
    TrainerDetailsRepository trainerDetailsRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SetupService setupService;
    @Autowired
    private Helper helper;

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

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = user.getUsername(); //get logged in username
        System.out.println("............." + name);
        APIResponseObj apiResponseObj = setupService.addCenter(centerVO);
        return new ResponseEntity<>(apiResponseObj, HttpStatus.OK);

    }


    @Transactional
    @RequestMapping(value = "/add-trainer", method = RequestMethod.POST)
    public ResponseEntity<?> addTrainer(@RequestBody TrainerDetails trainerDetailsVO) {

        System.out.println("print trainer details " + trainerDetailsVO.toString());

        setupService.addTrainer(trainerDetailsVO);
        System.out.println("user >> @@@ ### ");
        return new ResponseEntity<>(new APIResponseObj("SUCCESS", "", ""), HttpStatus.OK);

    }

    @Transactional
    @RequestMapping(value = "/edit-trainer", method = RequestMethod.POST)
    public ResponseEntity<?> editTrainer(@RequestBody TrainerDetails trainerDetailsVO) {

        setupService.editTrainer(trainerDetailsVO);
        System.out.println("user >> @@@ ### ");
        return new ResponseEntity<>(new APIResponseObj("SUCCESS", "", ""), HttpStatus.OK);

    }

    @Transactional
    @RequestMapping(value = "/add-mc", method = RequestMethod.POST)
    public ResponseEntity<?> addMc(@RequestBody Users userVO) {

        System.out.println("print trainer details " + userVO.toString());

        setupService.addMc(userVO);
        System.out.println("user >> @@@ ### ");
        return new ResponseEntity<>(new APIResponseObj("SUCCESS", "", ""), HttpStatus.OK);

    }

    @Transactional
    @RequestMapping(value = "/edit-mc", method = RequestMethod.POST)
    public ResponseEntity<?> editMc(@RequestBody Users userVO) {

        setupService.editMc(userVO);
        System.out.println("user >> @@@ ### ");
        return new ResponseEntity<>(new APIResponseObj("SUCCESS", "", ""), HttpStatus.OK);

    }

    // Center Admins


    @Transactional
    @RequestMapping(value = "/add-ca", method = RequestMethod.POST)
    public ResponseEntity<?> addCA(@RequestBody Users userVO) {

        System.out.println("print ca details " + userVO.toString());

        setupService.addCA(userVO);
        System.out.println("user >> @@@ ### ");
        return new ResponseEntity<>(new APIResponseObj("SUCCESS", "", ""), HttpStatus.OK);

    }

    @Transactional
    @RequestMapping(value = "/edit-ca", method = RequestMethod.POST)
    public ResponseEntity<?> editCA(@RequestBody Users userVO) {

        setupService.editCA(userVO);
        System.out.println("user >> @@@ ### ");
        return new ResponseEntity<>(new APIResponseObj("SUCCESS", "", ""), HttpStatus.OK);

    }


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

    @RequestMapping(value = "/add-services", method = RequestMethod.POST)
    public ResponseEntity<?> addServices(@RequestBody Services servicesVO) {
        String token = "";
        System.out.println("KLKLKL " + servicesVO.toString());

        ServiceCategory sc = new ServiceCategory();
        sc.setId(Long.valueOf(servicesVO.getSelectedcatid()));

        servicesVO.setServicecategory(sc);


        ServiceSubCategory ssc = new ServiceSubCategory();
        ssc.setId(Long.valueOf(servicesVO.getSelectedsubcatid()));
        servicesVO.setServicesubcategory(ssc);

        setupService.addServices(servicesVO);

        return new ResponseEntity<>(new APIResponseObj("SUCCESS", "", ""), HttpStatus.OK);


    }


}

