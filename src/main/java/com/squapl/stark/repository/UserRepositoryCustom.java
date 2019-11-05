package com.squapl.stark.repository;


import com.squapl.stark.model.TrainerDetails;
import com.squapl.stark.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepositoryCustom {

    public List<User> getAllProfiles(String center_id, String role_id, String status);

    public String getAllTrainers(String center_id, String role_id, String status);

    public int updateTrainerInfo(TrainerDetails trainerDetailsVO);

    public int updateMcInfo(User userVO);

    public int updateCAInfo(User userVO);

}

