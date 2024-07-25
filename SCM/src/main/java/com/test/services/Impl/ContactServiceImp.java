package com.test.services.Impl;

import java.util.List;
import java.util.UUID;
import com.test.helper.ResouceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.test.entities.Contact;
import com.test.entities.User;
import com.test.repo.ContactRepo;
import com.test.services.ContactService;

@Service
public class ContactServiceImp implements ContactService {

    @Autowired
    ContactRepo contactRepo;


    @Override
    public Contact save(Contact contact) {
        String contactId = UUID.randomUUID().toString();
        contact.setId(contactId);
        return contactRepo.save(contact);

    }

    @Override
    public Contact update(Contact contact) {
        var contactOld = contactRepo.findById(contact.getId()).orElseThrow(()->new ResouceNotFoundException("Contact Not Found!!"));
        contactOld.setName(contact.getName());
        contactOld.setEmail(contact.getEmail());
        contactOld.setPhone(contact.getPhone());
        contactOld.setDescription(contact.getDescription());
        contactOld.setAddress(contact.getAddress());
        contactOld.setPicture(contact.getPicture());
        // contactOld.setFav(contact.isFav());
        contactOld.setWebsiteLink(contact.getWebsiteLink());
        contactOld.setLinkedInLink(contact.getLinkedInLink());
        contactOld.setCloudinaryImgPubId(contact.getCloudinaryImgPubId());
        // contactOld.setSocialLinks(contact.getSocialLinks());
        return contactRepo.save(contactOld);


    }

    @Override
    public List<Contact> getALL() {
        return contactRepo.findAll();
    }

    @Override
    public Contact getById(String id) {
        return contactRepo.findById(id).orElseThrow(()->new ResouceNotFoundException("Contact Not found by given Id"+id));
    }

    @Override
    public void deleteContact(String id) {
        var contact = contactRepo.findById(id).orElseThrow(()->new ResouceNotFoundException("Contact Not found by given Id"+id));
        contactRepo.delete(contact);
    }


    @Override
    public List<Contact> getByUserId(String userId) {
        return contactRepo.findByUserId(userId);
    }

    @Override
    public Page<Contact> getByUser(User user,int page,int size, String sortBy,String direction) {
        
        Sort sort = direction.equals("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();

        var pagable = PageRequest.of(page, size,sort);


        return  contactRepo.findByUser(user, pagable);
    }

    @Override
    public  Page<Contact> searchByName(String name, int size, int page, String sortBy, String order,User user) {
        
        Sort sort = order.equals("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size,sort);
        return contactRepo.findByUserAndNameContaining(user,name,pageable);
    }

    @Override
    public  Page<Contact> searchByEmail(String email, int size, int page, String sortBy, String order,User user) {
        Sort sort = order.equals("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size,sort);
        return contactRepo.findByUserAndEmailContaining(user,email ,pageable);
    }

    @Override
    public Page<Contact> searchByPhone(String phone, int size, int page, String sortBy, String order,User user) {
        Sort sort = order.equals("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size,sort);
        return contactRepo.findByUserAndPhoneContaining(user,phone,pageable);
    }

   
    
}
