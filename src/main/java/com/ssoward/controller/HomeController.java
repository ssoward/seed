package com.ssoward.controller;

import com.ssoward.service.TestUtil;
import com.ssoward.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by ssoward on 2/22/14.
 */

@Controller
@RequestMapping("/api")
public class HomeController {

    @Autowired
    TestUtil testUtil;

    @Autowired
    UserService userService;


}
