package com.test.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Contact {
 
    @Id
    private String id;
   
    private String email;
  
    private String name;
    private String phone;
    private String address;
    private String picture;
    @Column(length = 1000)
    private String description;
    private boolean fav;
    private String websiteLink;
    private String linkedInLink;
    private String cloudinaryImgPubId;
    @ManyToOne
    @JsonIgnore
    private User user;

    

    // private List<String> SocielLink = new ArrayList<>();
    @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL, fetch = FetchType.EAGER,orphanRemoval = true)
    private List<SocialLink> socialLinks = new ArrayList<>();


}
