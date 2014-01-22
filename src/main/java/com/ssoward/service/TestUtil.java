package com.ssoward.service;


import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ssoward.model.Users;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;


/**
 * Created with IntelliJ IDEA.
 * User: ssoward
 * Date: 1/18/14
 * Time: 4:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestUtil {

    @Autowired
    JdbcTemplate template;

    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

    public List<Users> getStudentDetails() {
        List<Users> uList = new ArrayList<Users>();
        List<Map<String, Object>> l = template.queryForList("select * from user");
        for (Map<String, Object> m : l) {
            String name = (String) m.get("name");
            Long id = ((Integer) m.get("id")).longValue();
            Users u = new Users();
            u.setId(id);
            u.setName(name);
            uList.add(u);
        }
//   		Iterator it = studentList.iterator();
//   		while (it.hasNext()) {
//   			Object object = it.next();
//   			System.out.println(object.toString());
//   		}
        return uList;
    }
}
