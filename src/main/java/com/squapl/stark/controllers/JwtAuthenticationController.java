package com.squapl.stark.controllers;

import com.squapl.stark.config.JwtTokenUtil;
import com.squapl.stark.model.JwtRequest;
import com.squapl.stark.model.JwtResponse;
import com.squapl.stark.model.Users;
import com.squapl.stark.repository.RoleDao;
import com.squapl.stark.repository.UserRepository;
import com.squapl.stark.service.DwUtilService;
import com.squapl.stark.service.JwtUserDetailsService;
import com.squapl.stark.service.UserService;
import com.squapl.stark.util.APIResponseObj;
import com.squapl.stark.util.Helper;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
@RequestMapping("/api")
public class JwtAuthenticationController {

    private static final String SALT = "salt"; // Salt should be protected carefully
    @Autowired
    DwUtilService dwUtilService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private JwtUserDetailsService userDetailsService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private Helper helper;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        try {
            authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("TEST.. " + e.getMessage());

            if (e.getMessage().equalsIgnoreCase("INVALID_CREDENTIALS")) {
                Users user = userRepository.findByUsername(authenticationRequest.getUsername());
                if (user == null) {
                    return new ResponseEntity<>(new APIResponseObj("FAILURE", "USER_NOT_FOUND", ""), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(new APIResponseObj("FAILURE", "INVALID_CREDENTIALS", ""), HttpStatus.OK);
                }


            }

        }
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        Users user = userRepository.findByUsername(userDetails.getUsername());

        userService.assignRoleValues(userDetails, user);


        final String token = jwtTokenUtil.generateToken(userDetails);

        if (user.getVerified().equalsIgnoreCase("N")) {
            return new ResponseEntity<>(new APIResponseObj("FAILURE", "PENDING_VERIFICATION", user), HttpStatus.OK);
        }

        return new ResponseEntity<>(new APIResponseObj("SUCCESS", new JwtResponse(token).getToken(), user), HttpStatus.OK);
    }

//    @Transactional
//    @RequestMapping(value = "/register", method = RequestMethod.POST)
//    public ResponseEntity<?> saveUser(@RequestBody UserDTO user) throws Exception {
//        String token = "";
//        Users user1 = null;
//        String username = user.getUsername();
//        try {
//
//
//            user1 = new Users();
//            user1.setUsername(username);
//            user1.setPassword(user.getPassword());
//
//            Set<UserRole> userRoles = new HashSet<>();
//            userRoles.add(new UserRole(user1, roleDao.findByName("MEMBER")));
//
//            RawUser rawUser = dwUtilService.getRawUserDetails(username);
//
//            System.out.println("Raw User >>> " + rawUser);
//
//            if (rawUser == null) {
//                System.out.println("in...");
//                return new ResponseEntity<>(new APIResponseObj("FAILURE", "USER_NOT_EXIST", ""), HttpStatus.OK);
//            } else {
//
//                Users tmpUser = userService.getUserDetails(username);
//
//                if (tmpUser != null) {
//                    return new ResponseEntity<>(new APIResponseObj("FAILURE", "DUPLICATE_USER", ""), HttpStatus.OK);
//
//                }
//
//                long centerid = rawUser.getCenterid();
//
//                Long serviceId = dwUtilService.getServiceId(String.valueOf(centerid), "1");
//
//                Center tempCenter = new Center();
//                tempCenter.setId(centerid);
//
//                user1.setCenter(tempCenter);
//                user1.setVerified("N");
//                user1.setMobilenumber(username);
//                user1.setRole("member");
//                user1.setSignup_mode("E"); // E - Email
//                user1.setStatus("A");
//                user1.setFirstname(rawUser.getFirstname());
//
//                Users newUser = userService.createUser(user1, userRoles);
//
//                if (newUser != null) {
//
//
//                    userService.insertMember_services(String.valueOf(newUser.getId()), serviceId,
//                            rawUser.getStartdate(), rawUser.getEnddate());
//
//
//                    final UserDetails userDetails = userDetailsService
//                            .loadUserByUsername(user.getUsername());
//                    token = jwtTokenUtil.generateToken(userDetails);
//                }
//            }
//
//
//        } catch (Exception e) {
//            if (e.toString().indexOf("uname_uniq") != -1) {
//                return new ResponseEntity<>(new APIResponseObj("FAILURE", "DUPLICATE_USER", ""), HttpStatus.OK);
//            } else {
//                return new ResponseEntity<>(new APIResponseObj("FAILURE", "SERVER_ERROR", ""), HttpStatus.OK);
//            }
//
//        }
//
//        return new ResponseEntity<>(new APIResponseObj("SUCCESS", new JwtResponse(token).getToken(), user1), HttpStatus.OK);
//
//    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }


    @GetMapping(value = "/getuserinfo/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Users> getUserInfo(@PathVariable("username") String username) {

        Users user = userRepository.findByUsername(username);

        return new ResponseEntity<Users>(user, HttpStatus.OK);

    }

    @RequestMapping(
            value = "/sendotp",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity sendotp(@RequestBody String phonenumber, BindingResult bindingResult) {


        log.debug("sign in json:::::::;  " + phonenumber);
        System.out.println("phonenumber = [" + phonenumber + "] ");
        //  String resultBody = "";
        ResponseEntity<String> result = null;
        OTP otp = new OTP();

        try {

            RestTemplate restTemplate = new RestTemplate();


            String url = "https://2factor.in/API/V1/4d830b8e-7c1a-11e8-a895-0200cd936042/SMS/" + phonenumber + "/AUTOGEN" + "/confirm+pt+session";
            HttpHeaders headers = new HttpHeaders();

            HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
            result = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            //System.out.println("AA 1 >> " + result.getBody());

            // when
            JSONObject object = (JSONObject) JSONValue.parse(result.getBody());
            otp.setStatus((String) object.get("Status"));
            otp.setDetails((String) object.get("Details"));


        } catch (Exception e) {
            log.debug("Error while processing recover password " + e.getMessage());
            return new ResponseEntity<>(new APIResponseObj("ERROR", "", ""), HttpStatus.OK);

        }

        return new ResponseEntity<>(new APIResponseObj("SUCCESS", "OTP Sent", otp), HttpStatus.OK);

    }


    @RequestMapping(
            value = "/verifyotp/{otpsessionid}/{enteredotp}",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity verifyotp(@PathVariable("otpsessionid") String otpsessionid,
                                    @PathVariable("enteredotp") String enteredotp) {

        log.debug("sign in sessiondetails:::::::;  " + otpsessionid + "entered otp " + enteredotp);
        //  String resultBody = "";
        ResponseEntity<String> result = null;
        OTP otp = new OTP();

        try {

            RestTemplate restTemplate = new RestTemplate();
            //https://2factor.in/API/V1/{api_key}/SMS/VERIFY/{session_id}/{otp_input}

            String url = "https://2factor.in/API/V1/4d830b8e-7c1a-11e8-a895-0200cd936042/SMS/VERIFY/" + otpsessionid + "/" + enteredotp;
            HttpHeaders headers = new HttpHeaders();

            HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
            result = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            System.out.println("AA 1 >> " + result.getBody());

            // when
            JSONObject object = (JSONObject) JSONValue.parse(result.getBody());
            otp.setStatus((String) object.get("Status"));
            otp.setDetails((String) object.get("Details"));


        } catch (Exception e) {
            log.debug("Error while processing recover password " + e.getMessage());
            return new ResponseEntity<>(new APIResponseObj("ERROR", "", ""), HttpStatus.OK);
        }

        return new ResponseEntity<>(new APIResponseObj("SUCCESS", "OTP Verified", otp), HttpStatus.OK);

    }

    class OTP {

        private String status;
        private String details;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }


    }
}