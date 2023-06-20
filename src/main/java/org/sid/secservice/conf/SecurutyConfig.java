package org.sid.secservice.conf;

import java.util.ArrayList;
import java.util.Collection;

import org.sid.secservice.entities.AppUser;
import org.sid.secservice.filters.JwtAuthenticationFilter;
import org.sid.secservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
@EnableWebSecurity
public class SecurutyConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private AccountService accountService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		auth.userDetailsService(new UserDetailsService() {
			
		
			@Override
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				// TODO Auto-generated method stub
				AppUser appUser = accountService.loadUserByUserName(username);
				return new User(appUser.getUserName(), appUser.getPassword(), autorities(appUser));
			}
		});
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		http.csrf().disable();
		//pour la gestion des sessions 
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.headers().frameOptions().disable();
		//http.formLogin();
		//http.authorizeRequests().anyRequest().permitAll();
	    http.authorizeRequests().antMatchers("/swagger-ui.html/**").permitAll();
		http.authorizeRequests().antMatchers("/h2-console/**").permitAll();
		http.authorizeRequests().anyRequest().authenticated();
		http.addFilter(new JwtAuthenticationFilter(authenticationManagerBean()));
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManagerBean();
	}
	
	private Collection<GrantedAuthority> autorities(AppUser appUser){
		Collection<GrantedAuthority> listAutorities = new ArrayList<>();
		appUser.getAppRoles().forEach(r -> {
			listAutorities.add(new SimpleGrantedAuthority(r.getRoleName()));
		});
		return listAutorities;
	}
	
}
