package com.booksms.marketing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class MarketingApplication {

    public static void main(String[] args) {
        Environment environment = SpringApplication.run(MarketingApplication.class, args).getEnvironment();
        String username = environment.getProperty("GMAIL_USERNAME");
        System.out.println(username);
    }

}
