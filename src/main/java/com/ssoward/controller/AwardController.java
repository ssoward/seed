package com.ssoward.controller;

import com.ssoward.model.Award;
import com.ssoward.model.Give;
import com.ssoward.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ssoward on 4/18/14.
 */

@Controller
@RequestMapping("/api")
public class AwardController {

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

    @RequestMapping(method = RequestMethod.GET, value="/awards", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> fetchAwards(@RequestParam(value = "page", required = false, defaultValue = "0") int page) {
        List<Award> awards = awardsService.getAwards();
        return new ResponseEntity(awards, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value="/awards/{id}", produces = "image/jpg")
    @ResponseBody
    byte[] fetchAward(@PathVariable("id") Long id) {
        return fileService.getFile(id);
    }

    @RequestMapping(value = "/content-files/upload", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity uploadChunked(HttpServletRequest req, HttpServletResponse res) throws ServletException, Exception {
        fileService.uploadAward(req, res);

        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.DELETE, value="/awards", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteAward(@RequestParam Long id) {
        try {
            fileService.deleteFile(id);
            awardsService.deleteAward(id);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value="/awards", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateAward(@RequestBody Award award) {
        if(award != null){
            awardsService.updateAward(award);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}
