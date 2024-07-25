package com.test.Config;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.test.entities.Providers;
import com.test.entities.User;
import com.test.helper.AppConstants;
import com.test.repo.UserRepo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AthenticationHandler implements AuthenticationSuccessHandler {
	Logger logger = LoggerFactory.getLogger(AthenticationHandler.class);

	@Autowired
	private UserRepo userRepo;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		logger.info("Authenticationhandler");
		
		//Identify the provider
		
		var oauth2AthenticationToken = (OAuth2AuthenticationToken)authentication;
		
		String clientId =  oauth2AthenticationToken.getAuthorizedClientRegistrationId();
		
		logger.info(clientId);
		
		var oauthUser = (DefaultOAuth2User)authentication.getPrincipal();
		
		oauthUser.getAttributes().forEach((key,value)->{
			logger.info("{}->{}",key,value);
		});
		
		User user1 = new User();
		user1.setUserId(UUID.randomUUID().toString());
		user1.setRoleList(List.of(AppConstants.ROLE_USER));
		user1.setEmailVerify(true);
		user1.setEnable(true);

		
		if(clientId.equalsIgnoreCase("google")) {
			//google
			user1.setEmail(oauthUser.getAttribute("email").toString());
			user1.setName(oauthUser.getAttribute("name").toString());
			user1.setProfilePic(oauthUser.getAttribute("picture").toString());
			user1.setProviderUserId(oauthUser.getName());
			user1.setProvider(Providers.google);
			user1.setPassword("password");

			user1.setAbout("This is login using google");

		}
		else if(clientId.equalsIgnoreCase("github")){
			//github
			String email = oauthUser.getAttribute("email")!=null ? 
					oauthUser.getAttribute("email").toString()
					:oauthUser.getAttribute("login").toString()+"@gmail.com";
			String picture = oauthUser.getAttribute("avatar_url").toString();
			String name = oauthUser.getAttribute("login").toString();
			String providerId = oauthUser.getName();
			user1.setAbout("This is login using github");

//			
			user1.setEmail(email);
			user1.setProfilePic(picture);
			user1.setName(name);
			user1.setPassword("password");
			user1.setProviderUserId(providerId);
			user1.setProvider(Providers.github);
		}
		else if(clientId.equalsIgnoreCase("linkedin")){
			//linkedin
			
		}else {
			logger.info("Unknown Provider");
		}
		
		User user2 = userRepo.findByEmail(user1.getEmail()).orElse(null);
		if(user2==null)
		{
			userRepo.save(user1);
			logger.info("User Saved :"+user1.getEmail());
		}
		
		
		
		/*
		DefaultOAuth2User user = (DefaultOAuth2User)authentication.getPrincipal();
		
		
//		logger.info(user.getName()); 
//		
//		user.getAttributes().forEach((key,value)->{
//			logger.info("{}->{}",key,value);
//		});
//		logger.info(user.getAuthorities().toString());
		
		
		//Save to database
		String email = user.getAttribute("email").toString();
		String name = user.getAttribute("name").toString();
		String picture = user.getAttribute("picture").toString();
		
		User user1 = new User();
		user1.setEmail(email);
		user1.setName(name);
		user1.setProfilePic(picture);
		user1.setPassword("password");
		user1.setUserId(UUID.randomUUID().toString());
		user1.setProvider(Providers.google);
		user1.setEnable(true);
		user1.setEmailVerify(true);
		user1.setProviderUserId(user.getName());
		user1.setRoleList(List.of(AppConstants.ROLE_USER));
		user1.setAbout("This is login using google");
		
		User user2 = userRepo.findByEmail(email).orElse(null);
		if(user2==null)
		{
			userRepo.save(user1);
			logger.info("User Saved :"+email);
		}
		*/
		
		new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");
	}

	
}
