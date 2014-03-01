package com.ssoward.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ssoward.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;


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

    public List<Employee> getStudentDetails() {
        List<Employee> uList = new ArrayList<Employee>();
        List<Map<String, Object>> l = template.queryForList("select * from user");
        for (Map<String, Object> m : l) {
            String name = (String) m.get("name");
            Long id = ((Integer) m.get("id")).longValue();
            Employee u = new Employee();
            u.setId(id);
            u.setFirstName(name);
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
