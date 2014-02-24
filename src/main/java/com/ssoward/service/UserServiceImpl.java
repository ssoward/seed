package com.ssoward.service;

import com.ssoward.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by ssoward on 2/15/14.
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public User getLoggedInUser(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user;
    }

    @Override
    public List<Users> getUsers() {
        List<Users> uList = new ArrayList<Users>();
        List<Map<String, Object>> l = jdbcTemplate.queryForList("select * from users u join authorities a on a.username = u.username");
        for (Map<String, Object> m : l) {
            String name = (String) m.get("username");
            String auth = (String) m.get("authority");
            Users u = new Users();
            u.setAuth(auth);
            u.setFirstName(name);
            uList.add(u);
        }
        return uList;
    }

    @Override
    public void saveUser(Users praiser) {

        jdbcTemplate.update("insert into users values (?,?,1,?,?)",
                praiser.getEmail(),
                praiser.getPassword(),
                praiser.getFirstName(), praiser.getLastName());

        jdbcTemplate.update("insert into authorities values (?, 'ROLE_AUTH')",
                praiser.getEmail());

    }
}
