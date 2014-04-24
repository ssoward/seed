package com.ssoward.controller;

import com.ssoward.model.Give;
import com.ssoward.service.*;
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
 * Created by ssoward on 4/21/14.
 */

@Controller
@RequestMapping("/api")
public class GiveController {

    @Autowired
    TestUtil testUtil;

    @Autowired
    AwardsService awardsService;

    @Autowired
    GiveService giveService;

    @Autowired
    UserService userService;

    @Autowired
    ComplementsService complementService;

    @Autowired
    FileService fileService;

    @RequestMapping(method = RequestMethod.PUT, value="/gives/distributed", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity awardDistributed(@RequestBody Give give) {
        if(give != null){
            awardsService.awardDistributed(give);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value="/gives/logs", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> fetchPurchasedLogs(@RequestParam(value = "page", required = false, defaultValue = "0") int page) {
        List<Give> awards = giveService.fetchPurchasedLogs();
        return new ResponseEntity(awards, HttpStatus.OK);
    }

}
