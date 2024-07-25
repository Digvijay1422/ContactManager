package com.test.Config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.test.services.Impl.SecurityCustomUserDeatilService;


@Configuration
public class MyConfig {
    
	@Autowired
	private AuthFailurehandler authHandler;
	@Autowired
	private SecurityCustomUserDeatilService securityCustomUserDeatilService;
	
	@Autowired	
	private AuthenticationSuccessHandler handler;
	@Bean
	public AuthenticationProvider authenticationProvider()
	{
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(securityCustomUserDeatilService);
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		
		return daoAuthenticationProvider;
	}

	

	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception
	{
		//Configuration
		
		httpSecurity.authorizeHttpRequests(authorize->{
			authorize.requestMatchers("/user/**").authenticated();
			authorize.anyRequest().permitAll();
		});
		
		
		httpSecurity.formLogin(formLogin->{
			formLogin.loginPage("/login")
			.loginProcessingUrl("/authenticate")
			.successForwardUrl("/user/profile")
			.failureForwardUrl("/login?error=true")
			
			
			.usernameParameter("email")
			.passwordParameter("password");


			formLogin.failureHandler(authHandler);

		});



		
		httpSecurity.csrf(AbstractHttpConfigurer::disable);
		
		//OAuth Configuration
		httpSecurity.oauth2Login(oauth->{
			oauth.loginPage("/login")
			.successHandler(handler);
		});
		
		
		httpSecurity.logout(logout->{
			logout.logoutUrl("/do-logout")
			.logoutSuccessUrl("/login?logout=true");
		});
		
//		.failureHandler( new AuthenticationFailureHandler() {
//			
//			@Override
//			public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
//					AuthenticationException exception) throws IOException, ServletException {
//				// TODO Auto-generated method stub
//				
//			}
//		})
//		.successHandler( new AuthenticationSuccessHandler() {
//			
//			@Override
//			public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
//					Authentication authentication) throws IOException, ServletException {
//				// TODO Auto-generated method stub
//				
//			}
//		});
		return httpSecurity.build();
	}
    
}
