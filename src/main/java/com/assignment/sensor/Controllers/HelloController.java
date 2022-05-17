package com.assignment.sensor.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RequestMapping("/hello") // http://localhost:8080/hello
    public String index() {
        return "Greetings from Spring Boot!!!!!!";
    }

}
