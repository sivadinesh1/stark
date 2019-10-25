package com.squapl.stark.service;

import com.squapl.stark.model.User;
import com.squapl.stark.model.security.UserRole;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

public interface UserService {

    public User createUser(User user, Set<UserRole> userRoles);

    public User getUserDetails(String username);

    public int updateUserVerified(String userid);

    public User assignRoleValues(UserDetails userDetails, User user);

    public int insertMember_services(String userid, Long service_id, String start_date, String end_date);


}

