package com.ssoward.controller;

import com.ssoward.model.Users;
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

    //{"firstName":"scott","lastName":"soward","email":"amorvivir@yahoo.com","password":"password","passwordConfirm":"password"}
    @RequestMapping(method = RequestMethod.POST, value="/praiser", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity savePraiser(@RequestBody Users praiser) {
        if(praiser != null){
           userService.saveUser(praiser);
        }
        return new ResponseEntity(HttpStatus.CREATED);
    }

}
