package com.test.services;



public interface EmailService {
    

    void sendEmail(String to,String sub, String body);

    void sendEmailwithHtml();

    void sendEmailWithAttachments();
}
