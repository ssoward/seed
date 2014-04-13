package com.ssoward.controller;

import com.ssoward.model.Award;
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
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Iterator;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Created by ssoward on 3/17/14.
 */

@Controller
@RequestMapping("/api")
public class AdminController {


    @Autowired
    TestUtil testUtil;

    @Autowired
    AwardsService awardsService;

    @Autowired
    UserService userService;

    @Autowired
    ComplimentsService complimentService;

    @Autowired
    FileService fileService;

    @RequestMapping(value="/upload", method = RequestMethod.POST)
    public void UploadFile(MultipartHttpServletRequest request, HttpServletResponse response) {
        Iterator<String> itr=request.getFileNames();

        MultipartFile file=request.getFile(itr.next());

        String fileName=file.getOriginalFilename();
        System.out.println(fileName);
    }

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

}
