package com.example.blog.web.admin;

import com.example.blog.po.User;
import com.example.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class LoginController {
    @Autowired
    private UserService userService;
    @GetMapping
    public String loginPage(){
        return "admin/login";
    }
    /*判断用户的名称和密码是否正确*/
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session,
                        RedirectAttributes attributes){
        User user=userService.checkuser(username, password);
        if(user!=null){
            user.setPassword(null);
            session.setAttribute("user",user);
            return "admin/success";
        }else{
            attributes.addFlashAttribute("message","用户名或密码错误！");
           //不正确重新定向到登录页面
            return "redirect:/admin";
        }

    }
    /*注销功能*/
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin";
    }
}
