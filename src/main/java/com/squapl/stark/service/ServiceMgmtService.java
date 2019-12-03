package com.squapl.stark.service;

import com.squapl.stark.model.Services;
import org.json.simple.JSONArray;

import java.util.List;

public interface ServiceMgmtService {

//    public JSONArray getAllServices(String centerid, String category, String subcategory);

    public JSONArray getServices(String status, String centerid, String service_category_id);

    public List<Services> getAllServices(String status, String centerid);

    public JSONArray getTrainers(String centerid);

    public JSONArray getPackages(String centerid);

    public int insertUserServicesSelfEnquiry(String center_id, String e_user_id, Long service_id);

    public int checkExistingEnquiries(String center_id, String e_user_id, Long service_id);

    public JSONArray getServiceSubCatByCat(String center_id, String category_id);

}


