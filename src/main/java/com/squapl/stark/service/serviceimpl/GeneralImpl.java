package com.squapl.stark.service.serviceimpl;

import com.squapl.stark.model.Users;
import com.squapl.stark.repository.CenterRepository;
import com.squapl.stark.repository.UserRepository;
import com.squapl.stark.service.GeneralService;
import com.squapl.stark.util.Helper;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
@Slf4j
public class GeneralImpl implements GeneralService {

    @Autowired
    Helper helper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CenterRepository centerRepository;


    public JSONArray getCenterWiseInfo(String corporate_id, String center_id) {
        String returnStr = centerRepository.getCenterWiseInfo(corporate_id, center_id);
        return helper.getJSONArray(returnStr);
    }


    public List<Users> getAllProfiles(String center_id, String role_id, String status) {
        List userList = userRepository.getAllProfiles(center_id, role_id, status);
        return userList;
    }

    public JSONArray getAllTrainers(String center_id, String role_id, String status) {
        String returnStr = userRepository.getAllTrainers(center_id, role_id, status);
        return helper.getJSONArray(returnStr);
    }

}
