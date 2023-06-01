package com.sakshicodes.UserManager.controller;

import com.sakshicodes.UserManager.model.UserDtls;
import com.sakshicodes.UserManager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController
{

    @Autowired
    private UserService userService;
    @GetMapping("/")
    public String index()
    {
        return "index";
    }

    @GetMapping("/signin")
    public String login()
    {
        return "login";
    }

    @GetMapping("/register")
    public String register()
    {
        return "register";
    }

    @PostMapping("/createUser")
    public String createuser(@ModelAttribute UserDtls user, HttpSession session)
    {

       // System.out.println(user);

        boolean f=userService.checkEmail(user.getEmail());

        if(f)
        {
            session.setAttribute("msg","Email id Already Exist ! ");
        }
        else
        {

            UserDtls userDtls = userService.createUser(user);
            if (userDtls != null) {
                session.setAttribute("msg","Registered Successfully ! ");
            } else {
                session.setAttribute("msg","Something Wrong On Server ! ");
            }
        }
        return "redirect:/register";
    }
}