package com.example.blog.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutController {
    @GetMapping("/aboutme")
    public String about(){
        return "/aboutme";
    }

}
