package com.squapl.stark.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


@Configuration
@Profile({"dev", "default"})
public class DevSystemConfiguration implements SystemConfiguration {


    private String profileimagebaseuploadfolder = "/Users/sivadineshm/documents/backup/img/profiles/";
    private String paymentproofpicsuploadfolder = "/Users/sivadineshm/documents/backup/img/enrolments/";

    private String assessmenttemplatefolder = "/Users/sivadineshm/documents/products/befit/";
    private String assessmentoutputfolder = "/Users/sivadineshm/documents/products/befit/";

    private String memberupload = "";


    private String baseurl = "http://localhost";

    private String[] allowDomain = {"http://localhost:8100", "http://192.168.0.103", "*", "http://192.168.1.8", "http://192.168.1.8:8100", "http://localhost:8000", "http://192.168.0.103:8100", "http://192.168.0.99:8100",
            "http://localhost:8080", "http://localhost:80", "http://localhost:4200", "http://127.0.0.1:4200", "http://www.squapl.com", "squapl.com", "www.squapl.com", "http://localhost"};

    public String[] getAllowedDomains() {
        return this.allowDomain;
    }

    public String getBaseURL() {
        return this.baseurl;
    }


    public String getProfileImageBaseUploadFolder() {
        return this.profileimagebaseuploadfolder;
    }

    public String getPaymentProofPicsUploadFolder() {
        return this.paymentproofpicsuploadfolder;
    }

    public String getAssessmenttemplatefolder() {
        return this.assessmenttemplatefolder;
    }

    public String getAssessmentoutputfolder() {
        return this.assessmentoutputfolder;
    }

}
