package com.funnyx.user.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/user")
public class TestController {


    @GetMapping(value = "/test")
    public String testGateway(){
        return "This Request comes from Gateway!";
    }
}
