package com.church.appChurch.controller;

import com.church.appChurch.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloWorldController {

    @Autowired
    @Qualifier("v2")
    private IMessageService messageService;

    @GetMapping("/hello")
    public String sayHello() {
        return messageService.sayCustomMessage("Hello World");
    }




}
