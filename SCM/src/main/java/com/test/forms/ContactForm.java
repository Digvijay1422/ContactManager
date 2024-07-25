package com.test.forms;


import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ContactForm {
   
    private String name;
    private String email;
    private String description;
    private String address;
    private String phone;
    private boolean favorite=false;
    private String websiteLink;
    private String linkedinLink;
    private MultipartFile picture;
    private String stringPicture;

}
