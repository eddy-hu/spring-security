package org.thecodeschool.security.browser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.thecodeschool.security.core.properties.SecurityProperties;

@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private SecurityProperties securityProperties; 
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//http.httpBasic() prompt login
		http.formLogin()// form login
			.loginPage("/authentication/require")
			.loginProcessingUrl("/authentication/form")
			.and()
			.authorizeRequests()
			.antMatchers("/authentication/require",securityProperties.getBrowser().getLoginPage()).permitAll() //give the permission to login page
			.anyRequest()
			.authenticated()
			.and()
			.csrf().disable();
	}
	
}