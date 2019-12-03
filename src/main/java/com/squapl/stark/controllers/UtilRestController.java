package com.squapl.stark.controllers;

import com.paytm.pg.merchant.CheckSumServiceHelper;
import com.squapl.stark.model.PaytmDTO;
import com.squapl.stark.util.APIResponseObj;
import com.squapl.stark.util.Helper;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.TreeMap;

@RestController
@RequestMapping("/api")
@Slf4j
public class UtilRestController {

    private static String MID = "aLdtwd06336804469126";
    private static String MercahntKey = "aVbTNkIPJvduho8V";
    private static String INDUSTRY_TYPE_ID = "Retail";
    private static String CHANNLE_ID = "WEB";
    private static String WEBSITE = "WEBSTAGING";
    private static String CALLBACK_URL = "http://localhost:8080/api/callback/";

    @Autowired
    private Helper helper;


    @GetMapping(value = "/paytmgetchecksum",
            produces = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity payTMgetChecksum() {

        String orderid = Helper.getRandomAlphaNumericString(50);

        log.info("inisde paytmgetchecksum >> INFO ");
        log.debug("inisde paytmgetchecksum >> DEBUG ");
        TreeMap<String, String> paramMap = new TreeMap<String, String>();
        paramMap.put("MID", MID);
        paramMap.put("ORDER_ID", orderid);
        paramMap.put("CUST_ID", "CUST00011");
        paramMap.put("INDUSTRY_TYPE_ID", INDUSTRY_TYPE_ID);
        paramMap.put("CHANNEL_ID", CHANNLE_ID);
        paramMap.put("TXN_AMOUNT", "1.00");
        paramMap.put("WEBSITE", WEBSITE);
        paramMap.put("EMAIL", "test@gmail.com");
        paramMap.put("MOBILE_NO", "9999999999");
        paramMap.put("CALLBACK_URL", CALLBACK_URL);


        PaytmDTO paytmdto = new PaytmDTO();
        paytmdto.setMid(MID);
        paytmdto.setOrder_id(orderid);
        paytmdto.setCust_id("CUST00011");
        paytmdto.setIndustry_type_id(INDUSTRY_TYPE_ID);
        paytmdto.setChannel_id(CHANNLE_ID);
        paytmdto.setTxn_amount("1.00");
        paytmdto.setWebsite(WEBSITE);
        paytmdto.setEmail("test@gmail.com");
        paytmdto.setMobile_no("9999999999");
        paytmdto.setCallback_url(CALLBACK_URL);

        String checkSum = "";

        try {
            checkSum = CheckSumServiceHelper.getCheckSumServiceHelper().genrateCheckSum(MercahntKey, paramMap);
            paramMap.put("CHECKSUMHASH", checkSum);
            paytmdto.setChecksumhash(checkSum);

            System.out.println("Paytm Payload: " + paramMap);

            log.info("Paytm Payload: " + paramMap);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

//        return new ResponseEntity<>(new APIResponse("SUCCESS",HttpStatus.OK, checkSum), HttpStatus.OK);
        return new ResponseEntity<>(new APIResponseObj("SUCCESS", checkSum, paytmdto), HttpStatus.OK);


    }


    @PostMapping(value = "/payment-response",
            produces = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity payTMPaymentResponse(@RequestBody String stringToParse) {
        System.out.println("inside payment response..." + stringToParse);
        // RESPMSG

        return new ResponseEntity(null, HttpStatus.OK);
    }

    @PostMapping(value = "/callback",
            produces = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity payTMPaymentCallbackResponse(@RequestBody String stringToParse) {
        System.out.println("inside payment callback response..." + stringToParse);
        return new ResponseEntity(null, HttpStatus.OK);
    }


// place the jar file in correct location as per the command, dont install as sudo, run the code from the project base folder (not sure if it is mandatory)
    //  mvn install:install-file -Dfile=/Users/sivadineshm/Downloads/PaytmChecksum.jar  -DgroupId=com.paytm -DartifactId=paytm -Dversion=0.0.1  -Dpackaging=jar -DgeneratePom=true


    @RequestMapping(value = "/checksumverification", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity checksumVerification(@RequestBody String stringToParse) {

        JSONObject result = helper.getJsonObj(stringToParse);
        log.debug("PARAMS >> " + result.toJSONString());
        try {
            // Long customerid = (Long) result.get("customerid");

            String paytmChecksum = null;
// Create a tree map from the form post param
            //  TreeMap<String, String> paytmParams = new TreeMap<String, String>();

            paytmChecksum = "jDX3TYqPBuNDhPcHN3Eo7EpunNtJTGDk2YjE5MP9vlHMWMqCxJszWWFr4tLRHj1doIyC9a9dmOmU7yIsKPdG5aBkb2azVFLRvuupu1IvB6k=";
            TreeMap<String, String> paytmParams = new TreeMap<String, String>();
            paytmParams.put("MID", MID);
            paytmParams.put("ORDER_ID", "ORDER00011");
            paytmParams.put("CUST_ID", "CUST00011");
            paytmParams.put("INDUSTRY_TYPE_ID", INDUSTRY_TYPE_ID);
            paytmParams.put("CHANNEL_ID", CHANNLE_ID);
            paytmParams.put("TXN_AMOUNT", "1.00");
            paytmParams.put("WEBSITE", WEBSITE);
            paytmParams.put("EMAIL", "test@gmail.com");
            paytmParams.put("MOBILE_NO", "9999999999");
            paytmParams.put("CALLBACK_URL", CALLBACK_URL);


            boolean isValidChecksum = CheckSumServiceHelper.getCheckSumServiceHelper().verifycheckSum(MID, paytmParams, paytmChecksum);
// If isValidChecksum is false, then checksum is not valid
            if (isValidChecksum) {
                System.out.append("Checksum Matched");
            } else {
                System.out.append("Checksum MisMatch");
            }

        } catch (Exception e) {
            System.out.println("error....");
        }
        return new ResponseEntity(null, HttpStatus.OK);
    }
}





