package com.ssoward.controller;

import com.ssoward.model.Award;
import com.ssoward.service.AwardsService;
import com.ssoward.service.TestUtil;
import com.ssoward.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.naming.InsufficientResourcesException;

/**
 * Created by ssoward on 4/18/14.
 */
@Controller
@RequestMapping("/api")
public class BuckController {
    @Autowired
    TestUtil testUtil;

    @Autowired
    AwardsService awardsService;

    @RequestMapping(method = RequestMethod.PUT, value="/buck/buy", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateAward(@RequestBody Award award) {
        if(award != null){
            try {
                awardsService.decrementBucks(award);
            } catch (InsufficientResourcesException e) {
                e.printStackTrace();
                return new ResponseEntity(HttpStatus.CONFLICT);
            }
        }
        return new ResponseEntity(HttpStatus.OK);
    }


}
