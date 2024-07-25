package com.test.services;
import java.util.List;

import org.springframework.data.domain.Page;

import com.test.entities.Contact;
import com.test.entities.User;


public interface ContactService {
    
    Contact save(Contact contact);
    Contact update(Contact contact);
    List<Contact> getALL();
    Contact getById(String id);
    void deleteContact(String id);
    Page<Contact> searchByName(String name , int size, int page, String sortBy, String order,User user);
    Page<Contact> searchByEmail(String email , int size, int page, String sortBy, String order,User user);
    Page<Contact> searchByPhone(String phone , int size, int page, String sortBy, String order,User user);
    List<Contact> getByUserId(String userId);

    Page<Contact> getByUser(User user,int page ,int size,String sortBy,String direction);
}
