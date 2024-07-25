package com.test.Config;

import java.io.IOException;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.test.helper.Message;
import com.test.helper.MessageType;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class AuthFailurehandler implements AuthenticationFailureHandler{

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
                if(exception instanceof DisabledException){

                    HttpSession session = request.getSession();
                    Message  message = new Message("User is disabled, Verification link is send to to Email",MessageType.red);
		            session.setAttribute("message",message);
                    response.sendRedirect("/login");    

                }
                else{
                    response.sendRedirect("/login?error=true");

                }

    }
    
}
