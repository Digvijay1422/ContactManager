package com.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.entities.Contact;
import com.test.services.ContactService;
@RestController
@RequestMapping("/api")
public class ApiController {
    
    @Autowired
    ContactService contactService;
    //get contact
    @GetMapping("/contacts/{contactId}")
    public Contact getContact(@PathVariable String contactId)
    {
        return contactService.getById(contactId);

    }
}
