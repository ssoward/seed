package com.ssoward.service;

import org.springframework.security.core.userdetails.User;

/**
 * Created by ssoward on 2/15/14.
 */
public interface UserService {
    User getLoggedInUser();
}
