package com.ssoward.service;

import com.ssoward.model.Employee;
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
    JdbcTemplate jdbcTemplate;

    @Override
    public Employee getLoggedInUser(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return getUser(user.getUsername());
    }

    @Override
    public List<Employee> getUsers() {
        List<Employee> uList = new ArrayList<Employee>();
        List<Map<String, Object>> l = jdbcTemplate.queryForList("select * from users u join authorities a on a.username = u.username");
        buildUser(l, uList);
        return uList;
    }

    @Override
    public Employee getUser(String userName) {
        List<Employee> uList = new ArrayList<Employee>();
        List<Map<String, Object>> l = jdbcTemplate.queryForList("select * from users u join authorities a on a.username = u.username where a.username = ?", userName);
        buildUser(l, uList);
        return uList != null?uList.get(0):null;
    }

    private void buildUser(List<Map<String, Object>> l, List<Employee> uList) {
        for (Map<String, Object> m : l) {
            String email = (String) m.get("username");
            String auth = (String) m.get("authority");
            Employee u = new Employee();
            u.setFirstName((String) m.get("first_name"));
            u.setLastName((String) m.get("last_name"));
            u.setPassword((String) m.get("password"));
            u.setCount((Integer) m.get("count"));
            u.setAuth(auth);
            u.setEmail(email);
            uList.add(u);
        }
    }

    @Override
    public void saveUser(Employee praiser) {
        //check if this is an update to the user
        Integer i = jdbcTemplate.queryForObject("select count(*) from users where username = ?", new Object[] {praiser.getEmail()}, Integer.class);
        if(i == 1){
            boolean changePassword = true;
            //only change password if it has been changed from ****
            int ii = praiser.password.indexOf('*');
            if(ii > -1){
                changePassword = false;
            }
            if(changePassword){
                jdbcTemplate.update("update users set first_name = ?, last_name = ?, password = ?, count = ? where username = ?",
                        praiser.getFirstName(),
                        praiser.getLastName(),
                        praiser.password,
                        praiser.getCount(),
                        praiser.getEmail());
            }else{
                jdbcTemplate.update("update users set first_name = ?, last_name = ?, count = ? where username = ?",
                        praiser.getFirstName(),
                        praiser.getLastName(),
                        praiser.getCount(),
                        praiser.getEmail());
            }

            jdbcTemplate.update("update authorities set authority = ? where username = ?",
                    praiser.getAuth(),
                    praiser.getEmail());
        }
        else{
            jdbcTemplate.update("insert into users values (?,?,1,?,?,?)",
                    praiser.getEmail(),
                    praiser.getPassword(),
                    praiser.getFirstName(),
                    praiser.getLastName(),
                    praiser.getCount()
            );

            jdbcTemplate.update("insert into authorities values (?, 'ROLE_AUTH')",
                    praiser.getEmail());
        }
    }

    @Override
    public void deleteUser(String username) {
        jdbcTemplate.update("delete from users where username = ?", username);
        jdbcTemplate.update("delete from authorities where username = ?", username);
    }

    @Override
    public void saveUserCount(Employee praiser) {
        jdbcTemplate.update("update users set count = ? where username = ?", praiser.getCount(), praiser.getEmail());
    }

    @Override
    public void decrementCount(String praiser) {
        jdbcTemplate.update("update users set count = count-1 where username = ?", praiser);
    }
}
