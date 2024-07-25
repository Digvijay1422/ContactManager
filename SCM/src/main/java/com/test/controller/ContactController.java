package com.test.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.test.entities.Contact;
import com.test.entities.User;
import com.test.forms.ContactForm;
import com.test.forms.ContactSearchForm;
import com.test.helper.AppConstants;
import com.test.helper.Helper;
import com.test.helper.Message;
import com.test.helper.MessageType;
import com.test.services.ContactService;
import com.test.services.ImageService;
import com.test.services.UserService;

import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/user/contacts")
public class ContactController {

    @Autowired
    UserService userService;

    @Autowired
    private ImageService imageService;
    @Autowired
    ContactService contactService;
    

    @RequestMapping("/add")
    public String addContact(Model model)
    {
        ContactForm form = new ContactForm();
        model.addAttribute("form", form);
        return "user/addContact";
    }

    @PostMapping("/add")
    public String saveContact( @ModelAttribute ContactForm contactForm, HttpSession session,Authentication authentication)
    {


        // if(result.hasErrors())
        // {
        //     return "user/addContact";
        // }
        String username = Helper.getEmailOfLoggedInUser(authentication);

        User user  = userService.getUserByEmail(username);

        String fileName = UUID.randomUUID().toString();


        //image processing
        System.out.println("File info : "+contactForm.getPicture().getOriginalFilename());

        
        //process form data
        Contact contact = new Contact();
        contact.setName(contactForm.getName());
        contact.setEmail(contactForm.getEmail());
        contact.setPhone(contactForm.getPhone());
        contact.setFav(contactForm.isFavorite());
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setLinkedInLink(contactForm.getLinkedinLink());
        contact.setWebsiteLink(contactForm.getWebsiteLink());
        contact.setUser(user);
        contact.setCloudinaryImgPubId(fileName);
        if(contactForm.getPicture()!=null && !contactForm.getPicture().isEmpty()){
            String fileUrl = imageService.uploadImage(contactForm.getPicture(),fileName);
            contact.setPicture(fileUrl);
        }

        Message  message = new Message("Contact Added Successfully",MessageType.green);
		session.setAttribute("message",message);


        contactService.save(contact);

        System.out.println(contactForm);
        return "redirect:/user/contacts/add";
    }

    //View Contatcts

    @RequestMapping
    public String viewContacts(Model model,
    Authentication authentication,
    @ModelAttribute ContactSearchForm contactSearchForm,

    @RequestParam(value="page", defaultValue = "4")int page,
    @RequestParam(value="size", defaultValue="4")int size,
    @RequestParam(value="sortBy", defaultValue="name")String sortBy,
    @RequestParam(value="direction", defaultValue="asc")String direction

    )
    {

        String username = Helper.getEmailOfLoggedInUser(authentication);
        //load all users
        User user = userService.getUserByEmail(username);
        // System.out.println("User:"+use       rname);
        // System.out.println("User:"+user.getName());

        Page<Contact> pageContacts =  contactService.getByUser(user,page,size,sortBy,direction);

        // System.out.println(pageContacts.getContent());

        
        model.addAttribute("pageContacts", pageContacts);
        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
        model.addAttribute("contactSearchForm",  contactSearchForm);

        return "user/contacts";
    }

    @RequestMapping("/search")
    public String searchHandler(
        @ModelAttribute ContactSearchForm contactSearchForm,
       
        @RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE+"")int size,
        @RequestParam(value="page", defaultValue="0")int page,
        @RequestParam(value="sortBy", defaultValue="name")String sortBy,
        @RequestParam(value="direction", defaultValue="asc")String direction,
        Model model, Authentication authentication
    ){

        User user = userService.getUserByEmail(Helper.getEmailOfLoggedInUser(authentication));
        Page<Contact> contacts = null;
         if(contactSearchForm.getField().equalsIgnoreCase("name"))
         {
            contacts = contactService.searchByName(contactSearchForm.getValue(),size,page,sortBy,direction,user);
         }
         else if(contactSearchForm.getField().equalsIgnoreCase("email"))
         {
            contacts = contactService.searchByEmail(contactSearchForm.getValue(),size,page,sortBy,direction,user);
         }
         else if(contactSearchForm.getField().equalsIgnoreCase("phone"))
         {
           contacts =  contactService.searchByPhone(contactSearchForm.getValue(),size,page,sortBy,direction,user);
         }
         
         System.out.println(contacts);
         model.addAttribute("contacts", contacts);
         model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
         model.addAttribute("contactSearchForm", contactSearchForm);

        return "user/search";
    }
    

    //delete contact
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteContact( @PathVariable String id)
    {
        contactService.deleteContact(id);
        System.out.println("In delete");
        return "redirect:/user/contacts";
    }


    //update

    @GetMapping("/view/{id}")
    public String updateContactForm(@PathVariable String id,Model model)
    {

        var contact = contactService.getById(id);
        ContactForm contactForm = new ContactForm();
        contactForm.setName(contact.getName());
        contactForm.setEmail(contact.getEmail());
        contactForm.setPhone(contact.getPhone());
        contactForm.setAddress(contact.getAddress());
        
        contactForm.setDescription(contact.getDescription());
        contactForm.setFavorite(contact.isFav());
        contactForm.setWebsiteLink(contact.getWebsiteLink());
        contactForm.setLinkedinLink(contact.getLinkedInLink());
        contactForm.setStringPicture(contact.getPicture());

        model.addAttribute("contactForm",contactForm);
        model.addAttribute("id",id);
        return "user/update_contact_view";
    }

    @PostMapping("/update/{id}")
    public String updateContactData( @PathVariable String id , @ModelAttribute ContactForm contactForm,BindingResult bindingResult, Model model, HttpSession session){

        if(bindingResult.hasErrors()){
            return "/user/contacts/update_contact_view";
        }
        var con = contactService.getById(id);
        con.setId(id);
        con.setName(contactForm.getName());
        con.setEmail(contactForm.getEmail());
        con.setPhone(contactForm.getPhone());
        con.setAddress(contactForm.getAddress());
        con.setDescription(contactForm.getDescription());
        con.setFav(contactForm.isFavorite());
        con.setWebsiteLink(contactForm.getWebsiteLink());
        con.setLinkedInLink(contactForm.getLinkedinLink());
        // con.setPicture(contactForm.getStringPicture());
        //process image


        if(contactForm.getPicture()!=null && !contactForm.getPicture().isEmpty())
        {
            System.out.println("not empty");

        String fileName = UUID.randomUUID().toString();
        String imgUrl =  imageService.uploadImage(contactForm.getPicture(), fileName );
        con.setCloudinaryImgPubId(fileName);
        con.setPicture(imgUrl);
        contactForm.setStringPicture(imgUrl);
        }
        Message  message = new Message("Contact Updated Successfully",MessageType.green);
		session.setAttribute("message",message);
        contactService.update(con);
        return "redirect:/user/contacts/view/"+id;

    }
}
