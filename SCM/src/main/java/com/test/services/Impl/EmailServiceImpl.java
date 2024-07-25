package com.test.services.Impl;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.test.services.EmailService;

@Service
public class EmailServiceImpl implements EmailService{

    @Autowired
    private JavaMailSender mailSender;

  

//     public EmailServiceImpl(JavaMailSender mailSender){
//         this.mailSender = mailSender;
//     }


    @Override
    public void sendEmail(String to, String sub, String body) {
        try {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo(to);
            msg.setSubject(sub);
            msg.setText(body);
            msg.setFrom("demo@demomailtrap.com");
            
            mailSender.send(msg);
            System.out.println("Sent");
        } catch (Exception e) {
                e.printStackTrace();
        }
    }

    @Override
    public void sendEmailwithHtml() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sendEmailwithHtml'");
    }

    @Override
    public void sendEmailWithAttachments() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sendEmailWithAttachments'");
    }
    

}
