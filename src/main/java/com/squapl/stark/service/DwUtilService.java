package com.squapl.stark.service;

import com.squapl.stark.model.RawUser;
import org.json.simple.JSONArray;

public interface DwUtilService {

    public int addRawUser(RawUser rawUser);

    public int deleteDWUserTbl(String centerid);

    public int updateMigrationStatus(String agent, String event, String entity, String status);


    public JSONArray getActiveMemberServies(String userid);

    public JSONArray getTrainersActiveServies(String userid);

    public JSONArray getServiceLogs(String userid, String service_id);

    public JSONArray getPaymentStatement(String userid, String service_id);

    public String getCenterId(String source_centerid);

    public int migrationDWtoRAW(String centerid);


    public RawUser getRawUserDetails(String username);

    public Long getServiceId(String center_id, String service_category_id);


}

