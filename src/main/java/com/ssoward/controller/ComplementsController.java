package com.ssoward.controller;

import com.ssoward.model.Complement;
import com.ssoward.service.ComplementsService;
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
public class ComplementsController {

    @Autowired
    TestUtil testUtil;

    @Autowired
    UserService userService;

    @Autowired
    ComplementsService complementService;

    @RequestMapping(method = RequestMethod.POST, value="/complements", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity saveComplement(@RequestBody Complement complement) {
        if(complement != null){
            complementService.saveComplement(complement);
        }
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/complements", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<Complement> getComplements(HttpServletRequest request) {
        return complementService.getComplements();
    }

    @RequestMapping(method = RequestMethod.DELETE, value="/complements", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteEmployee(@RequestParam Long id) {
        complementService.deleteComplement(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
