package com.squapl.stark.service;

import com.squapl.stark.model.Users;
import com.squapl.stark.model.security.UserRole;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

public interface UserService {

    public Users createUser(Users user, Set<UserRole> userRoles);

    public Users getUserDetails(String username);

    public int updateUserVerified(String userid);

    public Users assignRoleValues(UserDetails userDetails, Users user);

    public int insertMember_services(String userid, Long service_id, String start_date, String end_date);


}

