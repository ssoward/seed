package com.ssoward.controller;


import com.ssoward.model.Employee;
import com.ssoward.model.Praise;
import com.ssoward.service.PraiseService;
import com.ssoward.service.TestUtil;
import com.ssoward.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    PraiseService praiseService;

    @RequestMapping(method = RequestMethod.POST, value="/praise", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity savePraise(@RequestBody Praise praise) {
        if(praise != null){
            praiseService.savePraise(praise);
            userService.decrementCount(praise.getPraiser());
        }
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/praises", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<Praise> getPraises(HttpServletRequest request) {
        return praiseService.getPraises();
    }

    @RequestMapping(method = RequestMethod.DELETE, value="/praise", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteEmployee(@RequestParam Long id) {
        praiseService.deletePraise(id);
        return new ResponseEntity(HttpStatus.OK);
    }

}