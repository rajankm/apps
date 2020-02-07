package com.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.client.RestTemplate;

import com.auth.service.CustomOidcUserService;
import com.auth.service.HttpCookieOAuth2AuthorizationRequestRepository;
import com.auth.service.OAuth2AuthenticationFailureHandler;
import com.auth.service.OAuth2AuthenticationSuccessHandler;

@Order(1000)
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService;
	@Autowired
	private CustomOidcUserService oidcUserService;

	@Autowired
	private HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository;

	@Autowired
	private OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
	@Autowired
	private OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

/*	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().csrf()
		.disable().formLogin().disable().httpBasic().disable().authorizeRequests().antMatchers("/", "/**")
		.permitAll().anyRequest().authenticated().and().oauth2Login().authorizationEndpoint()
		.baseUri("/login/oauth2/authorization").authorizationRequestRepository(cookieAuthorizationRequestRepository).and()
		.redirectionEndpoint().baseUri("/login/oauth2/callback/*").and().userInfoEndpoint().userService(oauth2UserService)
		.and().successHandler(oAuth2AuthenticationSuccessHandler)
		.failureHandler(oAuth2AuthenticationFailureHandler);
	}*/
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().formLogin().disable().authorizeRequests().antMatchers("/", "/**").permitAll().and()
		.oauth2Login().authorizationEndpoint().baseUri("/login/oauth2/authorization")
		.authorizationRequestRepository(authorizationRequestRepository())
		.and().redirectionEndpoint().baseUri("/login/**")
		.and().userInfoEndpoint().oidcUserService(oidcUserService).userService(oauth2UserService)
		.and().successHandler(oAuth2AuthenticationSuccessHandler).failureHandler(oAuth2AuthenticationFailureHandler);

	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public AuthorizationRequestRepository<OAuth2AuthorizationRequest> authorizationRequestRepository() {
		return new HttpSessionOAuth2AuthorizationRequestRepository();
	}

	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
