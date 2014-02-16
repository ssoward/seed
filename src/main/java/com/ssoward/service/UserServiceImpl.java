package com.ssoward.service;

import com.ssoward.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ssoward on 2/15/14.
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    JdbcTemplate template;

    @Override
    public User getLoggedInUser(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user;
    }

    @Override
    public List<Users> getUsers() {
        List<Users> uList = new ArrayList<Users>();
        List<Map<String, Object>> l = template.queryForList("select * from users u join authorities a on a.username = u.username");
        for (Map<String, Object> m : l) {
            String name = (String) m.get("username");
            String auth = (String) m.get("authority");
            Users u = new Users();
            u.setAuth(auth);
            u.setName(name);
            uList.add(u);
        }
        return uList;
    }
}
