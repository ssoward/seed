package com.ssoward.service;

import com.ssoward.model.Employee;
import com.ssoward.model.Give;
import org.springframework.security.core.userdetails.User;

import java.util.List;

/**
 * Created by ssoward on 2/15/14.
 */
public interface UserService {
    Employee getLoggedInUser();
    List<Employee> getUsers();
    public List<Give> getGives();
    Employee getUser(String userName);
    void saveUser(Employee praiser);
    void deleteUser(String username);
    void saveUserCount(Employee praiser);
    void decrementCount(String praiser);
}
