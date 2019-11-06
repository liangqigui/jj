package com.example.jj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class JjApplication {

    public static void main(String[] args) {
        SpringApplication.run(JjApplication.class, args);

    }

}
