package com.auth.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.auth.model.dto.UserDto;

@Service
public class AuthUserDetailsService implements UserDetailsService {

	@Autowired
	@Qualifier("userApiService")
	private APIService apiService;
	
	private Map<String, UserDetails> userDetailsCache =  new ConcurrentHashMap<String, UserDetails>();
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetails user = userDetailsCache.get(username);
		if(user==null) {
			user = getUserFromAPI(username);
			userDetailsCache.put(username, user);
		}
		return user;
	}
	
	public User getUserFromAPI(String username) {
		Map<String, String> uriVariables = new HashMap<String, String>();
		uriVariables.put("username", username);
		UserDto userDto = apiService.getForEntity(uriVariables, UserDto.class).getBody();
		return new User(userDto.getEmail(), userDto.getPassword(), userDto.getAuthorities());
		
	}
}
