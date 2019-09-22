package com.appsdevblog.app.ws.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * This Class is designated for reading properties from app.property file
 */

@Component
public class AppProperties {

    // Note: For Environment object to be available for AppProperties class, it needs to be Autowired!
    @Autowired
    private Environment env;

    public String getTokenSecret() {
        return env.getProperty("tokenSecret");
    }
}
