package com.example.blog.web;

import com.example.blog.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class Testcontroller {
    @GetMapping(value = "/")
    public String  testhtml(){
       /*
        String blog=null;
        if(blog==null){
            throw  new NotFoundException("找不到");
        }*/

        return "index";
    }
}
