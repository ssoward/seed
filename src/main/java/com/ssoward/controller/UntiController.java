package com.ssoward.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by ssoward on 2/15/14.
 */

@Controller
@RequestMapping("/")
public class UntiController {

    @RequestMapping(method = RequestMethod.GET, value="/logout", produces = MediaType.APPLICATION_JSON_VALUE)
    public void logout(HttpServletRequest request) {
        request.getSession().invalidate();
    }
}
