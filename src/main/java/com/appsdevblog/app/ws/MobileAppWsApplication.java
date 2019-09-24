package com.appsdevblog.app.ws;

import com.appsdevblog.app.ws.security.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class MobileAppWsApplication extends SpringBootServletInitializer {

    /**
     * Extends SpringBootServletInitializer class and override configure method
     * in order to package this application as a Web Archive (WAR), change the packaging in pom.xml
     * to WAR and add spring-boot-starter-tomcat dependency with provided scope
     **/

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MobileAppWsApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(MobileAppWsApplication.class, args);
    }

    // We need to annotate this class with @Bean annotation to be able to autowire it into the userService.
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SpringApplicationContext springApplicationContext() {
        return new SpringApplicationContext();
    }

    @Bean(name = "AppProperties")
    public AppProperties getAppProperties() {
        return new AppProperties();
    }

}
