package com.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.test.entities.User;
import com.test.helper.Helper;
import com.test.services.UserService;

@ControllerAdvice
public class RootController {

	@Autowired
	UserService userService;
	@ModelAttribute
	public void loggedInUserInfo(Model model,Authentication authentication)
	{
		if(authentication==null)
				return;
		String username = Helper.getEmailOfLoggedInUser(authentication);
    	
    	User user = userService.getUserByEmail(username);

    	// System.out.println(user.getEmail());
    	// System.out.println(user.getName());
    		
    	model.addAttribute("loggedInUser",user);
	}
    
}
