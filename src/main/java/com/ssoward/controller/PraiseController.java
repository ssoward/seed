package com.ssoward.controller;


import com.ssoward.model.Give;
import com.ssoward.service.GiveService;
import com.ssoward.service.TestUtil;
import com.ssoward.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.naming.InsufficientResourcesException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* Created with IntelliJ IDEA.
* User: ssoward
* Date: 1/18/14
* Time: 5:50 PM
* To change this template use File | Settings | File Templates.
*/

@Controller
@RequestMapping("/api")
public class PraiseController {

    @Autowired
    TestUtil testUtil;

    @Autowired
    UserService userService;

    @Autowired
    GiveService giveService;

    @RequestMapping(method = RequestMethod.POST, value="/praise", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity givePraise(@RequestBody Give give) {
        if(give != null){
            //TODO check there are gives available for this praise.
            try {
                userService.distributeGive(give);
            } catch (InsufficientResourcesException e) {
                e.printStackTrace();
                return new ResponseEntity(HttpStatus.CONFLICT);
            }
        }
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/give/praises", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<Give> getAllPraises(HttpServletRequest request) {
        return giveService.getAllPraises();
    }

}