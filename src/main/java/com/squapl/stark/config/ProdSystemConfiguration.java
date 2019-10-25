package com.squapl.stark.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("prod")
public class ProdSystemConfiguration implements SystemConfiguration {

    private String profileimagebaseuploadfolder = "/var/www/html/befit/img/profiles/";

    private String paymentproofpicsuploadfolder =  "/var/www/html/befit/img/enrolments/";

    private String assessmenttemplatefolder =  "/var/www/html/befit/assessments/";
    private String assessmentoutputfolder =  "/var/www/html/befit/assessments/";

    private String baseurl = "http://befit.squapl.com";

    private String[] allowDomain = {"https://myprofitgym.com", "https://www.myprofitgym.com", "http://localhost:8080", "https://www.befit.squapl.com", "https://befit.squapl.com", "http://www.befit.squapl.com", "https://befit.squapl.com:8080", "http://befit.squapl.com:8080", "http://befit.squapl.com", "www.befit.squapl.com", "http://ec2-52-77-210-225.ap-southeast-1.compute.amazonaws.com"};

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

    public String getAssessmenttemplatefolder() { return this.assessmenttemplatefolder; }

    public String getAssessmentoutputfolder() { return this.assessmentoutputfolder; }


}
