package io.springbootlab.springsecurityjwt.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping(value = "/home")
public class HomeResource {

    @RequestMapping(value = "/hello")
    public String sayHello(){
        return "Welcome User!\n\t"+LocalDateTime.now() +"";
    }
}
