package com.squapl.stark.service;

import org.json.simple.JSONArray;

public interface ServiceMgmtService {

    public JSONArray getAllServices(String centerid, String category, String subcategory);

    public JSONArray getTrainers(String centerid);

    public JSONArray getPackages(String centerid);

    public int insertUserServicesSelfEnquiry(String center_id, String e_user_id, Long service_id);

    public int checkExistingEnquiries(String center_id, String e_user_id, Long service_id);

}


