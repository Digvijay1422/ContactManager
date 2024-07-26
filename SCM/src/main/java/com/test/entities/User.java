package com.test.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;


@Entity
public class User implements UserDetails{
    @Id
    private String userId;
	@NotNull
	@Size(min = 2, max = 20,message = "Name size should be between 2 to 20")
    private String name;
	@NotNull
	@Size(min = 6,message = "Password size shoeld be minimun 6")
    private String password;
    private String role;
    @Column(unique = true , nullable = false)
    private String email;
    @Column(length = 1000)
    private String about;
    @Column(length = 1000)
    private String profilePic;
    @Getter(value = AccessLevel.NONE)
    private boolean enable = false;
    private boolean emailVerify = false;
    private String phone;
    private boolean phoneVerified;

	private String emailToken;
    public String getEmailToken() {
		return emailToken;
	}


	public void setEmailToken(String emailToken) {
		this.emailToken = emailToken;
	}


	//Self, google,facebook
    @Enumerated(value = EnumType.STRING)
    private Providers provider = Providers.Self;
    private String providerUserId;


    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
    private List<Contact> contact = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roleList = new ArrayList<String>();
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<SimpleGrantedAuthority> roles =  roleList.stream()
				.map(role->new SimpleGrantedAuthority(role)).collect(Collectors.toList());
		return roles;
	}


	@Override
	public String getPassword() {
		return this.password;
	}


	@Override
	public String getUsername() {
		return this.email;
	}


	@Override
	public boolean isAccountNonExpired() {
		return true;
	}


	@Override
	public boolean isAccountNonLocked() {
		return true;
	}


	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}


	@Override
	public boolean isEnabled() {
		return this.enable;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getRole() {
		return role;
	}


	public void setRole(String role) {
		this.role = role;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getAbout() {
		return about;
	}


	public void setAbout(String about) {
		this.about = about;
	}


	public String getProfilePic() {
		return profilePic;
	}


	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}


	public boolean isEnable() {
		return enable;
	}


	public void setEnable(boolean enable) {
		this.enable = enable;
	}


	public boolean isEmailVerify() {
		return emailVerify;
	}


	public void setEmailVerify(boolean emailVerify) {
		this.emailVerify = emailVerify;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public boolean isPhoneVerified() {
		return phoneVerified;
	}


	public void setPhoneVerified(boolean phoneVerified) {
		this.phoneVerified = phoneVerified;
	}


	public Providers getProvider() {
		return provider;
	}


	public void setProvider(Providers provider) {
		this.provider = provider;
	}


	public String getProviderUserId() {
		return providerUserId;
	}


	public void setProviderUserId(String providerUserId) {
		this.providerUserId = providerUserId;
	}


	public List<Contact> getContact() {
		return contact;
	}


	public void setContact(List<Contact> contact) {
		this.contact = contact;
	}


	public List<String> getRoleList() {
		return roleList;
	}


	public void setRoleList(List<String> roleList) {
		this.roleList = roleList;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	@Override
	public String toString() {
		return "User [userId=" + userId + ", name=" + name + ", password=" + password + ", role=" + role + ", email="
				+ email + ", about=" + about + ", profilePic=" + profilePic + ", enable=" + enable + ", emailVerify="
				+ emailVerify + ", phone=" + phone + ", phoneVerified=" + phoneVerified + ", provider=" + provider
				+ ", providerUserId=" + providerUserId + ", contact=" + contact + ", roleList=" + roleList + "]";
	}


	
	
	

   
}

