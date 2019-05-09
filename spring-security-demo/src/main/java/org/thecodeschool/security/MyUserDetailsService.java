package org.thecodeschool.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

@Component //register as a bean
public class MyUserDetailsService implements UserDetailsService, SocialUserDetailsService{
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	//@Autowired
	//UserService
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		logger.info("Login Username: "+username);
		//use userservice to check the username on database, get the password and authorities from database.
		
		//Spring security will validate the password and authentication.
		
		//we can create a User model to implements UserDetails interface or use Spring Security's User Class
		
		//another constructor to handle more validations
		//new User(username, username, true, true, true, false, AuthorityUtils.commaSeparatedStringToAuthorityList("admin")); 
		//logger.info(passwordEncoder.encode(username));
		
		return new User(username,passwordEncoder.encode("password"),AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
	}

	@Override
	public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
		logger.info("Login Username: "+userId);
		
		return new SocialUser(userId,passwordEncoder.encode("password"),AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
	}

}
