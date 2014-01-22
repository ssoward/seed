package com.ssoward.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created with IntelliJ IDEA.
 * User: ssoward
 * Date: 1/18/14
 * Time: 5:50 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class HelloController {

    @RequestMapping(value="/yo", method=RequestMethod.GET)
    public String index() {
        return "index";
    }

//    @RequestMapping(value="/enter", method=RequestMethod.GET)
//    public String login() {
//        return "login";
//    }
}