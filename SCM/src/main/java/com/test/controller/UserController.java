package com.test.controller;


import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/user")
public class UserController {

	
	
    //Dashboard
    @RequestMapping(value="/dashboard")
    public String userDashboard()
    {

        return "user/dashboard";
    }

    //Profile page
    @RequestMapping(value="/profile")
    public String profilePage(Model model,Authentication authentication)
    {
    	

        return "user/profile";
    }
    
}
