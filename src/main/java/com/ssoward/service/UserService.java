package com.ssoward.service;

import com.ssoward.model.Users;
import org.springframework.security.core.userdetails.User;

import java.util.List;

/**
 * Created by ssoward on 2/15/14.
 */
public interface UserService {
    User getLoggedInUser();

    List<Users> getUsers();
}
