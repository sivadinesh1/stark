package com.squapl.stark.controllers;

import com.squapl.stark.model.JwtResponse;
import com.squapl.stark.service.DwUtilService;
import com.squapl.stark.service.ServiceMgmtService;
import com.squapl.stark.service.UserService;
import com.squapl.stark.util.APIResponseObj;
import com.squapl.stark.util.Helper;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
public class ServiceMgmtController {

    @Autowired
    DwUtilService dwUtilService;

    @Autowired
    private UserService userService;

    @Autowired
    private ServiceMgmtService serviceMgmtService;

    @Autowired
    private Helper helper;

    @RequestMapping(
            value = "/getallservices/{center_id}/{category_id}/{subcategory_id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAllServices(@PathVariable("center_id") String center_id,
                                         @PathVariable("category_id") String category_id,
                                         @PathVariable("subcategory_id") String subcategory_id) {

        JSONArray restultJArr = serviceMgmtService.getAllServices(center_id, category_id, subcategory_id);

        return new ResponseEntity<>(new APIResponseObj("SUCCESS", "", restultJArr), HttpStatus.OK);
    }


    @RequestMapping(
            value = "/gettrainers/{center_id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getTrainers(@PathVariable("center_id") String center_id) {

        JSONArray restultJArr = serviceMgmtService.getTrainers(center_id);

        return new ResponseEntity<>(new APIResponseObj("SUCCESS", "", restultJArr), HttpStatus.OK);
    }

    @RequestMapping(
            value = "/getpackages/{centerid}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getPackages(@PathVariable("centerid") String centerid) {

        JSONArray restultJson = serviceMgmtService.getPackages(centerid);

        return new ResponseEntity<>(new APIResponseObj("SUCCESS", "", restultJson), HttpStatus.OK);
    }


    @Transactional
    @RequestMapping(value = "/insert-user-services-self-enquiry", method = RequestMethod.POST)

    public ResponseEntity<?> insertUserServicesSelfEnquiry(@RequestBody String stringToParse) throws Exception {
        String token = "";
        JSONObject result = helper.getJsonObj(stringToParse);
        log.debug("PARAMS >> " + result.toJSONString());

        try {

            String service_id = (String) result.get("service_id");
            String e_user_id = (String) result.get("user_id");
            String center_id = (String) result.get("center_id");

            int count = serviceMgmtService.checkExistingEnquiries(center_id, e_user_id, Long.valueOf(service_id));

            if (count == 0) {
                serviceMgmtService.insertUserServicesSelfEnquiry(center_id, e_user_id, Long.valueOf(service_id));
            } else if (count > 1) {
                return new ResponseEntity<>(new APIResponseObj("FAILURE", "DUPLICATE_ERROR", ""), HttpStatus.OK);
            }


        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new APIResponseObj("FAILURE", "SERVER_ERROR", ""), HttpStatus.OK);


        }

        return new ResponseEntity<>(new APIResponseObj("SUCCESS", new JwtResponse(token).getToken(), ""), HttpStatus.OK);


    }


}

