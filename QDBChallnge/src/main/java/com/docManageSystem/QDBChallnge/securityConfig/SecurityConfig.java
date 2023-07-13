package com.docManageSystem.QDBChallnge.securityConfig;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;



@Configuration
@EnableWebSecurity
//@EnableMethodSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	auth
	.inMemoryAuthentication()
	.withUser("user").password("{noop}password").roles("USER")
	.and()
	.withUser("admin").password("{noop}password").roles("ADMIN")
	.and()
	.withUser("editor").password("{noop}password").roles("EDITOR");
	
	}
	
//	@SuppressWarnings("deprecation")
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	http
	.authorizeRequests()
	.antMatchers("/upload").hasRole("ADMIN")
	.antMatchers("/download/**").hasAnyRole("ADMIN","USER")
	.antMatchers("/delete/**").hasAnyRole("ADMIN","EDITOR")
	.antMatchers("/getAllDocs/**").hasAnyRole("ADMIN","USER")
	.antMatchers("/getAllPosts").hasAnyRole("ADMIN","USER")
	.antMatchers("/createComment/**").hasAnyRole("ADMIN","USER")
	.antMatchers("/getAllComments/**").hasAnyRole("ADMIN","USER")
//	.antMatchers("/h2-console/**").hasRole("ADMIN")
	.anyRequest().authenticated()
	.and()
	.csrf()
    .ignoringAntMatchers("/upload","/delete/**","/createComment/**")
    .and()
	.httpBasic();
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
	    web.ignoring().antMatchers("/h2-console/**");
	}

	
}
