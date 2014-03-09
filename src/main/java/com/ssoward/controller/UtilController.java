package com.ssoward.controller;

import com.ssoward.model.Employee;
import com.ssoward.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by ssoward on 2/15/14.
 */

@Controller
@RequestMapping("/")
public class UtilController {

     @Autowired
     UserService userService;

    @RequestMapping(method = RequestMethod.GET, value="/logout", produces = MediaType.APPLICATION_JSON_VALUE)
    public void logout(HttpServletRequest request) {
        request.getSession().invalidate();
    }

}
