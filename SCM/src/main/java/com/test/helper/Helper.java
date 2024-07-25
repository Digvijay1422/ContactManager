package com.test.helper;


import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class Helper {

	@SuppressWarnings("null")
	public static String getEmailOfLoggedInUser(Authentication authentication)
	
	{
		if(authentication instanceof OAuth2AuthenticationToken)
		{
			
			var aOAuth2AuthenticationToken = (OAuth2AuthenticationToken)authentication;
			var clientId = aOAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
			
			var oAuth2User = (OAuth2User)authentication.getPrincipal();
			String username = null;
			if(clientId.equalsIgnoreCase("google")) {
				username = oAuth2User.getAttribute("email").toString();
				
			}else if(clientId.equalsIgnoreCase("github"))
			{
				username = oAuth2User.getAttribute("email")!=null ? 
						oAuth2User.getAttribute("email").toString()
						:oAuth2User.getAttribute("login").toString()+"@gmail.com";

			}
			//google
			//Github
			
			
			
			return username;
		}
		//email pass
		else {
			return authentication.getName();
		}
		
		
	}
}

