package com.squapl.stark.repository;


import com.squapl.stark.model.TrainerDetails;
import com.squapl.stark.model.Users;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepositoryCustom {

    public List<Users> getAllProfiles(String center_id, String role_id, String status);

    public String getAllTrainers(String center_id, String role_id, String status);

    public int updateTrainerInfo(TrainerDetails trainerDetailsVO);

    public int updateMcInfo(Users userVO);

    public int updateCAInfo(Users userVO);

}

