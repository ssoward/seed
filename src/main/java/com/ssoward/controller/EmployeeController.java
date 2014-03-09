package com.ssoward.controller;

import com.ssoward.model.Employee;
import com.ssoward.service.TestUtil;
import com.ssoward.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by ssoward on 3/1/14.
 */

@Controller
@RequestMapping("/api")
public class EmployeeController {
    @Autowired
    TestUtil testUtil;

    @Autowired
    UserService userService;

    @RequestMapping(value="/yo", method= RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping(method = RequestMethod.POST, value="/employee", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity saveEmployee(@RequestBody Employee praiser) {
        if(praiser != null){
            userService.saveUser(praiser);
        }
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT, value="/employee/count", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity saveEmployeeCount(@RequestBody Employee praiser) {
        if(praiser != null){
            userService.saveUserCount(praiser);
        }
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.DELETE, value="/employee", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteEmployee(@RequestParam String username) {
        userService.deleteUser(username);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value="/employees", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> leader(@RequestParam(value = "page", required = false, defaultValue = "0") int page) {
        List<Employee> users = userService.getUsers();
        for(Employee emp: users){
            emp.setPassword("************");
        }
        return new ResponseEntity(users, HttpStatus.OK);
    }
}
