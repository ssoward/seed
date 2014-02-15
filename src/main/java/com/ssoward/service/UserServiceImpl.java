package com.ssoward.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

/**
 * Created by ssoward on 2/15/14.
 */

@Service
public class UserServiceImpl implements UserService {

    @Override
    public User getLoggedInUser(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user;
    }
}
