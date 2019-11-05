package com.squapl.stark.controllers;

import com.squapl.stark.repository.UserRepository;
import com.squapl.stark.service.DwUtilService;
import com.squapl.stark.service.UserService;
import com.squapl.stark.util.APIResponseObj;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    DwUtilService dwUtilService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userdao;


    @RequestMapping(
            value = "/getactivememberservices/{user_id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getActiveMemberServices(@PathVariable("user_id") String user_id) {

        JSONArray restultJArr = dwUtilService.getActiveMemberServies(user_id);

        return new ResponseEntity<>(new APIResponseObj("SUCCESS", "", restultJArr), HttpStatus.OK);
    }


//    @RequestMapping(
//            value = "/getpackages/{centerid}/{productcategory}",
//            method = RequestMethod.GET,
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity getPackages(@PathVariable("centerid") String centerid, @PathVariable("productcategory") String productcategory) {
//
//        JSONObject restultJson = dwUtilService.getPackages(centerid, productcategory);
//
//        return new ResponseEntity<>(new APIResponseObj("SUCCESS", HttpStatus.OK, "", restultJson), HttpStatus.OK);
//    }


    @RequestMapping(
            value = "/updateuserverified/{username}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateUserVerified(@PathVariable("username") String username) {
        System.out.println("UserController.updateUserVerified" + username);
        int rowcount = userService.updateUserVerified(username);

        return new ResponseEntity<>(new APIResponseObj("SUCCESS", "", rowcount), HttpStatus.OK);
    }


}



