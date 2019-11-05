package com.squapl.stark.controllers;

import com.squapl.stark.Exception.EntityNotFoundException;
import com.squapl.stark.model.User;
import com.squapl.stark.repository.UserRepository;
import com.squapl.stark.service.DwUtilService;
import com.squapl.stark.service.GeneralService;
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

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class GeneralController {

    @Autowired
    DwUtilService dwUtilService;

    @Autowired
    private UserService userService;
    @Autowired
    private GeneralService generalService;
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(
            value = "/getcenterwiseinfo/{userid}/{role}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getCenterWiseInfo(@PathVariable("userid") String userid, @PathVariable("role") String role) {

        User user = userRepository.findById(Long.valueOf(userid))
                .orElseThrow(() -> new EntityNotFoundException("userid {" + userid + "}"));

        String corporate_id = null;
        String center_id = null;
        JSONArray restultJArr = null;

        if (role.equalsIgnoreCase("center")) {
            center_id = String.valueOf(user.getCenter().getId());
        } else if (role.equalsIgnoreCase("corporate")) {
            corporate_id = String.valueOf(user.getCorporate().getId());
        }

        try {
            restultJArr = generalService.getCenterWiseInfo(corporate_id, center_id);
        } catch (Exception e) {
            return new ResponseEntity<>(new APIResponseObj("FAILURE", "", restultJArr), HttpStatus.OK);
        }


        return new ResponseEntity<>(new APIResponseObj("SUCCESS", "", restultJArr), HttpStatus.OK);
    }


    @RequestMapping(
            value = "/getallprofiles/{center_id}/{role_id}/{status}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAllProfiles(@PathVariable("center_id") String center_id, @PathVariable("role_id") String role_id, @PathVariable("status") String status) {

        List<User> restultJArr = generalService.getAllProfiles(center_id, role_id, status);

        return new ResponseEntity<>(new APIResponseObj("SUCCESS", "", restultJArr), HttpStatus.OK);
    }


    @RequestMapping(
            value = "/getalltrainers/{center_id}/{role_id}/{status}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAllTrainers(@PathVariable("center_id") String center_id, @PathVariable("role_id") String role_id, @PathVariable("status") String status) {

        JSONArray restultJArr = generalService.getAllTrainers(center_id, role_id, status);

        return new ResponseEntity<>(new APIResponseObj("SUCCESS", "", restultJArr), HttpStatus.OK);
    }

}

