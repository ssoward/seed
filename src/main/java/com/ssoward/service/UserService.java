package com.ssoward.service;

import com.ssoward.model.Award;
import com.ssoward.model.Employee;
import com.ssoward.model.Give;
import org.springframework.security.core.userdetails.User;

import javax.naming.InsufficientResourcesException;
import java.util.List;

/**
 * Created by ssoward on 2/15/14.
 */
public interface UserService {
    Employee getLoggedInUser();
    List<Employee> getUsers();
    Employee getUser(String userName);
    void saveUser(Employee praiser);
    void deleteUser(String username);
    void saveUserCount(Employee praiser);

    void distributeGive(Give give) throws InsufficientResourcesException;

}
