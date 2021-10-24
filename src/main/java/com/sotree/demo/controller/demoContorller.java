package com.sotree.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class demoContorller {
    @RequestMapping(value="/")
    public String home() {
        return "index";
    }
}
