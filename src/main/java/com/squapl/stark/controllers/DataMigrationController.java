package com.squapl.stark.controllers;

import com.squapl.stark.model.RawUser;
import com.squapl.stark.service.DwUtilService;
import com.squapl.stark.util.APIResponseObj;
import com.squapl.stark.util.Helper;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
@RequestMapping("/api")
@Slf4j
public class DataMigrationController {

    @Autowired
    DwUtilService dwUtilService;

    @RequestMapping({"/hello"})
    public String firstPage() {
        return "Hello World";
    }

    @RequestMapping(
            value = "/getdata/{centerid}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getdata(@PathVariable("centerid") String centerid) {

        ResponseEntity<String> result = null;


        try {

            RestTemplate restTemplate = new RestTemplate();


            String url = "https://backstage.yoactiv.com/EnquiryAPI.asmx/Getmemberdetail?api_key=zy1XetAv2CHT72mPbd2eZ6/KP0pIqi0ppmAgBHmmlo&Business_ID=" + centerid + "&Filter_Member=0";

            HttpHeaders headers = new HttpHeaders();

            HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
            result = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            // when
            JSONObject object = (JSONObject) JSONValue.parse(result.getBody());

            if (object != null) {
                dwUtilService.updateMigrationStatus("yoactive", "daily_user_migration", centerid, "Started...");

                int deleteflag = dwUtilService.deleteDWUserTbl(centerid);

                boolean success = processExternalJsonObj(object, centerid);

                if (success) {
                    dwUtilService.updateMigrationStatus("yoactive", "daily_user_migration", centerid, "success");
                    System.out.println("111");
                    dwUtilService.migrationDWtoRAW(centerid);
                    System.out.println("222");
                }

            }


        } catch (Exception e) {
            dwUtilService.updateMigrationStatus("yoactive", "daily_user_migration", centerid, "failed");
            return new ResponseEntity<>(new APIResponseObj("FAILURE", "Data Retrieved FAILED", ""), HttpStatus.OK);
        }

        return new ResponseEntity<>(new APIResponseObj("SUCCESS", "Data Retrieved", ""), HttpStatus.OK);

    }

    public boolean processExternalJsonObj(JSONObject dataJsonObj, String source_centerid) {

        boolean success = true;
        String centerid = dwUtilService.getCenterId(source_centerid);

        try {

            Helper helper = new Helper();

            dataJsonObj.keySet().forEach(keyResultsStr ->
            {


                Object keyResultsValue = dataJsonObj.get(keyResultsStr);

                JSONArray keyResultsValueArray = helper.getJSONArray(keyResultsValue.toString());

                // Convert the Value Object to JSONArrays

                keyResultsValueArray.forEach(item -> {
                    RawUser rawUserObj = new RawUser();

                    JSONObject keyResultsValueObj = (JSONObject) item;

                    keyResultsValueObj.keySet().forEach(element -> {

                        if (element.toString().equalsIgnoreCase("centerid")) {
                            Object centeridElem = keyResultsValueObj.get(element);
                            rawUserObj.setSource_centerid(String.valueOf(centeridElem));
                            rawUserObj.setCenterid(Long.valueOf(centerid));
                        }
                        if (element.toString().equalsIgnoreCase("firstname")) {
                            String firstnameElem = (String) keyResultsValueObj.get(element);
                            rawUserObj.setFirstname(firstnameElem);
                        }
                        if (element.toString().equalsIgnoreCase("mobilenumber")) {
                            String mobilenumberElem = (String) keyResultsValueObj.get(element);
                            rawUserObj.setMobilenumber(mobilenumberElem);
                        }

                        if (element.toString().equalsIgnoreCase("isactive")) {
                            String isactiveElem = (String) keyResultsValueObj.get(element);
                            rawUserObj.setIsactive(isactiveElem);
                        }


                        if (element.toString().equalsIgnoreCase("serviceDetails")) {

                            Object serviceDetailsValue = keyResultsValueObj.get(element);


                            if (serviceDetailsValue != null && serviceDetailsValue.toString().length() > 2) {


                                JSONObject serviceDetailsValueObj = helper.getGeneric(serviceDetailsValue.toString());

                                serviceDetailsValueObj.keySet().forEach(e -> {

                                    String serviceDetailElement = (String) serviceDetailsValueObj.get(e);

                                    if (e.toString().equalsIgnoreCase("startdate")) {
                                        rawUserObj.setStartdate(serviceDetailElement);
                                    }

                                    if (e.toString().equalsIgnoreCase("enddate")) {
                                        rawUserObj.setEnddate(serviceDetailElement);
                                    }


                                    if (e.toString().equalsIgnoreCase("servicevariation")) {
                                        rawUserObj.setServicevariation(serviceDetailElement);
                                    }


                                });
                            } else {
                                rawUserObj.setStartdate(null);
                                rawUserObj.setEnddate(null);
                                rawUserObj.setServicevariation(null);

                            }

                        }


                    });

                    //  System.out.println("test .." + rawUserObj);

                    dwUtilService.addRawUser(rawUserObj);
                });


            });

        } catch (Exception e) {
            dwUtilService.updateMigrationStatus("yoactive", "daily_user_migration", centerid, "failed");
            e.printStackTrace();
            System.out.println("XXXXX" + e.getMessage());
            return success;
        }
        return success;
    }


    // public JSONObject getPackages(String centerid, String productcategory);


    @RequestMapping(
            value = "/gettrainersactiveservies/{user_id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getTrainersActiveServies(@PathVariable("user_id") String user_id) {

        JSONArray restultJArr = dwUtilService.getTrainersActiveServies(user_id);

        return new ResponseEntity<>(new APIResponseObj("SUCCESS", "", restultJArr), HttpStatus.OK);
    }


    @RequestMapping(
            value = "/getservicelogs/{user_id}/{service_id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getServiceLogs(@PathVariable("user_id") String user_id, @PathVariable("service_id") String service_id) {

        JSONArray restultJArr = dwUtilService.getServiceLogs(user_id, service_id);

        return new ResponseEntity<>(new APIResponseObj("SUCCESS", "", restultJArr), HttpStatus.OK);
    }


    @RequestMapping(
            value = "/getpaymentstatement/{user_id}/{service_id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getPaymentStatement(@PathVariable("user_id") String user_id, @PathVariable("service_id") String service_id) {

        JSONArray restultJArr = dwUtilService.getPaymentStatement(user_id, service_id);

        return new ResponseEntity<>(new APIResponseObj("SUCCESS", "", restultJArr), HttpStatus.OK);
    }


    @RequestMapping(
            value = "/getrawuserdetails/{username}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity getRawUserDetails(@PathVariable("username") String username) {
        System.out.println("inside getRawUserDetails >> " + username);

        RawUser restult = dwUtilService.getRawUserDetails(username);

        return new ResponseEntity<>(new APIResponseObj("SUCCESS", "", restult), HttpStatus.OK);
    }

}


//{"Results":
//        [{"id":"284538","centerid":1312,"firstname":"lalhmunmawii","mobilenumber":"7628073868",
//        "serviceDetails":[{"servicevariation":"Gym Membership 3 Months","startdate":"09-09-2019","enddate":"08-12-2019"}],"isactive":"Y"}
//        ]
//}


