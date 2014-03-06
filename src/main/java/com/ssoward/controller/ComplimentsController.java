package com.ssoward.controller;

import com.ssoward.model.Compliment;
import com.ssoward.service.ComplimentsService;
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
 * Created by ssoward on 3/5/14.
 */

@Controller
@RequestMapping("/api")
public class ComplimentsController {

    @Autowired
    TestUtil testUtil;

    @Autowired
    UserService userService;

    @Autowired
    ComplimentsService complimentService;

    @RequestMapping(method = RequestMethod.POST, value="/compliments", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity saveCompliment(@RequestBody Compliment compliment) {
        if(compliment != null){
            complimentService.saveCompliment(compliment);
        }
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/compliments", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<Compliment> getCompliments(HttpServletRequest request) {
        return complimentService.getCompliments();
    }

    @RequestMapping(method = RequestMethod.DELETE, value="/compliments", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteEmployee(@RequestParam Long id) {
        complimentService.deleteCompliment(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
