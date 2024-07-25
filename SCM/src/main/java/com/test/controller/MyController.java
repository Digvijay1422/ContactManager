package com.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.test.forms.UserForm;
import com.test.entities.User;
import com.test.helper.Message;
import com.test.helper.MessageType;
import com.test.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class MyController {

	@Autowired
	private UserService userService;

	@GetMapping("/")
	public String index()
	{
		return "redirect:/Home";
	}
	@RequestMapping("/Home")
	public String Home(Model model)
	{

		model.addAttribute("name", "Project");
		return "Home";
	}
	@RequestMapping("/about")
	public String about(Model model)
	{

		model.addAttribute("name", "Project");
		return "about";
	}
	@RequestMapping("/services")
	public String sercices(Model model)
	{

		model.addAttribute("name", "Project");
		return "services";
	}

	@GetMapping("/contact")
	public String contact(Model model)
	{

		return "contact";
	}
	@GetMapping("/login")
	public String login(Model model)
	{
		return "login";
	}
	
	@GetMapping("/register")
	public String register(Model model)
	{

		UserForm  userForm = new UserForm();
		model.addAttribute("userForm", userForm) ;
		return "register";
	}
	
	

	//processing register
	@PostMapping("/do_register")
	public String processRegister(@Valid @ModelAttribute UserForm userForm,BindingResult bindingResult, HttpSession session)
	{
		//Fetch form data
		//Save data
		//Validate
		System.out.println(userForm);
		if(bindingResult.hasErrors()){
			return "register";
		}
		
		// User user = User.builder()
		// .name(userForm.getName())
		// .email(userForm.getEmail())
		// .password(userForm.getPassword())
		// .about(userForm.getAbout())
		// .phone(userForm.getPhone())
		// .profilePic("C:\\Users\\omkar\\Downloads\\contact.png")
		// .build();

		User user = new User();
		user.setName(userForm.getName());
		user.setEmail(userForm.getEmail());
		user.setPassword(userForm.getPassword());
		user.setAbout(userForm.getAbout());
		user.setPhone(userForm.getPhone());
		user.setEnable(false);
		user.setProfilePic("C:\\Users\\omkar\\Downloads\\contact.png");

		User savedUser = userService.saveUser(user);
		System.out.println(savedUser);


		
		// message
		Message  message = new Message("Registration Successful",MessageType.green);

		session.setAttribute("message",message);
		
		//Redirect
		return "redirect:/register";
	}
	
}
