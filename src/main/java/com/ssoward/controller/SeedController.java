package com.ssoward.controller;

import com.ssoward.model.Users;
import com.ssoward.service.TestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ssoward
 * Date: 1/18/14
 * Time: 4:14 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/api")
public class SeedController {

    @Autowired
    TestUtil testUtil;

    @RequestMapping(method = RequestMethod.GET, value="/scouts", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getscout(@RequestParam(value = "page", required = false, defaultValue = "0") int page) {
        return "hello world, you must be a scout or at least have the right permissions."; //new ResponseEntity<>("hello scout", HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value="/leaders", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> leader(@RequestParam(value = "page", required = false, defaultValue = "0") int page) {
       List<Users> users = testUtil.getStudentDetails();

        return new ResponseEntity(users, HttpStatus.OK);
    }

}
