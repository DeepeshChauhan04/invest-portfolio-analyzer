package com.sharemarket.invest.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class SwaggerTestController {

        @GetMapping
        public String hello() {
            return "Hello, Swagger!";
        }
}
