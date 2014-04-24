package com.ssoward.controller;

import com.ssoward.model.Award;
import com.ssoward.model.Employee;
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
    ComplementsService complementService;

    @Autowired
    FileService fileService;

    @Autowired
    ScheduledTaskService scheduledTaskService;

    @RequestMapping(method = RequestMethod.GET, value = "/loggedIn", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Employee getLoggedInUser(HttpServletRequest request) {
        scheduledTaskService.loadUserTasks();
        Employee e = userService.getLoggedInUser();
        return e;
    }

}
