package com.ssoward.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

/**
 * Created by ssoward on 2/4/14.
 */
@Component
public class StartupBanner implements CommandLineRunner {
    static final Logger LOG = Logger.getLogger(StartupBanner.class.getName());

    @Override public void run(String... args) throws Exception {
        LOG.severe("\n\n\n" +
                " ____  _____ _____ ____  \n" +
                "/ ___|| ____| ____|  _ \\ \n" +
                "\\___ \\|  _| |  _| | | | |\n" +
                " ___) | |___| |___| |_| |\n" +
                "|____/|_____|_____|____/ \n" +
                "                         \n                                                                                                                             \n" +
                "\n" +
                "\n\n\n");
    }
}