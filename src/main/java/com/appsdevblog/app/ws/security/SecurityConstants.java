package com.appsdevblog.app.ws.security;

import com.appsdevblog.app.ws.SpringApplicationContext;

public class SecurityConstants {

    public static final long EXPIRATION_TIME = 864000000; // 10 Days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/users";

    public static String getTokenSecret() {
        /**
         * Note: To access classes "components" which were created by Spring Framework such as the AppProperties Class
         * from the SecurityConstants, we will have to access it via the SpringApplicationContext "It accesses Beans
         * which were created by Spring Framework".
         */
        AppProperties appProperties = (AppProperties) SpringApplicationContext.getBean("AppProperties");
        return appProperties.getTokenSecret();
    }
}

