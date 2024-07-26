package com.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.test.entities.User;
import com.test.helper.Message;
import com.test.helper.MessageType;
import com.test.repo.UserRepo;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    private UserRepo userRepo;

    @GetMapping("/verify-email")
    public String verifyEmail( @RequestParam("token") String token, HttpSession session)
    {
        User user = userRepo.findByEmailToken(token).orElse(null);

        System.out.println(token);
        System.out.println(user.getEmailToken());
        if(user!=null){

            if(user.getEmailToken().equals(token)){
                user.setEmailVerify(true);
                user.setEnable(true);
                userRepo.save(user);
                  Message  message = new Message("Email verified Successfully, Your Accound has been enabaled",MessageType.green);
		        session.setAttribute("message",message);
                return "succes_page";
            }
            Message  message = new Message("Something went wrong!! Email Not verified.",MessageType.red);
            session.setAttribute("message",message);
            return "error_page";
        }
        Message  message = new Message("Something went wrong!! Email Not verified.",MessageType.red);
		session.setAttribute("message",message);
        return "error_page";
        
    }
}
