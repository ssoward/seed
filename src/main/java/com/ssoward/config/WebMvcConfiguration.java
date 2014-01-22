package com.ssoward.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * User: ssoward
 * Date: 1/20/14
 * Time: 10:17 AM
 * To change this template use File | Settings | File Templates.
 */

@EnableWebMvc
@ComponentScan("com.ssoward")
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {


//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/login").setViewName("login");
//        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
//    }
}
