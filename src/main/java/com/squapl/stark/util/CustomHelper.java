package com.squapl.stark.util;


import com.squapl.stark.config.SystemConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class CustomHelper {
    @Autowired
    private SystemConfiguration systemconfiguration;

    @Autowired
    private Helper helper;

    public String getLoggedInUserName() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getUsername(); //get logged in username
    }


}
