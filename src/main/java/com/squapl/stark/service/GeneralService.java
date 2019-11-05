package com.squapl.stark.service;

import com.squapl.stark.model.User;
import org.json.simple.JSONArray;

import java.util.List;

public interface GeneralService {
    public JSONArray getCenterWiseInfo(String corporate_id, String center_id);

    public List<User> getAllProfiles(String center_id, String role_id, String status);

    public JSONArray getAllTrainers(String center_id, String role_id, String status);
}

