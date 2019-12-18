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
public class UserDetailsServiceImpl implements UserDetailsService, AuthUserDetailsService {

	@Autowired
	@Qualifier("userApiService")
	private APIService apiService;
	
	private Map<String, UserDto> userDetailsCache =  new ConcurrentHashMap<String, UserDto>();
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDto user = getUserFromAPI(username);
		return new User(user.getEmail(), user.getPassword(), user.getAuthorities());
	}
	@Override
	public UserDto getUserDetails(String username) {
		UserDto user = userDetailsCache.get(username);
		if(user==null) {
			user = getUserFromAPI(username);
			userDetailsCache.put(username, user);
			}
		return user;
	}
	
	private UserDto getUserFromAPI(String username) {
		Map<String, String> uriVariables = new HashMap<String, String>();
		uriVariables.put("username", username);
		UserDto userDto = apiService.getForEntity(uriVariables, UserDto.class).getBody();
		return userDto;
		
	}

	@Override
	public void removeCachedUser(String username) throws UsernameNotFoundException {
		UserDto user = userDetailsCache.get(username);
		if(user!=null) {
			userDetailsCache.remove(username);
		}
	}
}
