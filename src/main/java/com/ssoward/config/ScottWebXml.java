package com.ssoward.config;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.SpringBootServletInitializer;



/**
 * Created with IntelliJ IDEA.
 * User: ssoward
 * Date: 1/18/14
 * Time: 5:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class ScottWebXml extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

}