package com.squapl.stark.service;

import org.json.simple.JSONArray;

public interface GeneralService {

    public void captureError();

    public JSONArray getCenterWiseInfo(String corporate_id, String center_id);

    public JSONArray getAllProfiles(String center_id, String role_id, String status);

}

