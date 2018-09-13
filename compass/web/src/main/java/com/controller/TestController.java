package com.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/web")
public class TestController {

	@GetMapping("hello/{value}")
    public String index(@PathVariable("value")String value) {
        return "Greetings from Spring Boot!"+value;
    }
}
