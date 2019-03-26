package edu.usf.cse;

import edu.usf.cse.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MainApplication {

    @Autowired
    UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

}