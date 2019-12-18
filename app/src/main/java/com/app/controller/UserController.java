package com.app.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.app.config.MessageConverter;
import com.app.entity.dto.UserDto;

@RestController
@RequestMapping("/user")
public class UserController {
	
	
	@RequestMapping(path = {"/form"}, method = RequestMethod.GET )
	public ModelAndView showForm(HttpServletRequest request, Model model) {
		ModelAndView mv = new ModelAndView();
		mv.addObject(new UserDto());
		mv.setViewName("login");
		return mv;
	}
	@PostMapping(path = {"/login"})
	public RedirectView login(@ModelAttribute UserDto user, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		RedirectView rv = new RedirectView(request.getContextPath()+"/home");
		String url = "http://localhost:8082/authenticate/user";
		RestTemplate restTemplate = new RestTemplate();
		try {
			ResponseEntity<UserDto> responseEntity = restTemplate.postForEntity(url, user, UserDto.class);
			session.setAttribute(HttpHeaders.AUTHORIZATION, responseEntity.getHeaders().getFirst(HttpHeaders.AUTHORIZATION));
			user = responseEntity.getBody();
			session.setAttribute("_", user);
		}catch(HttpClientErrorException httpEx){
			httpEx.printStackTrace();
		}catch(Exception ex) {
			if(ex instanceof ResourceAccessException) {
				System.out.println(ex.getMessage());
			}
			ex.printStackTrace();
		}
		return rv;
	}
	@GetMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse resposne, HttpSession session) {
		String uri = "http://localhost:8082/authenticate/logout";
		RestTemplate restTemplate = new RestTemplate();
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(HttpHeaders.AUTHORIZATION, session.getAttribute(HttpHeaders.AUTHORIZATION).toString());
			HttpEntity<?> httpEntity = new  HttpEntity<>(headers);
			ResponseEntity<String> responseEntity = restTemplate.exchange(new URI(uri), HttpMethod.GET, httpEntity , String.class);
			
		}catch(HttpClientErrorException httpEx){
			httpEx.printStackTrace();
		}catch(Exception ex) {
			if(ex instanceof ResourceAccessException) {
				System.out.println(ex.getMessage());
			}
			ex.printStackTrace();
		}
		return "login";
	}
	@PostMapping
	public HttpEntity<?> addUser(@ModelAttribute("user") UserDto user) throws URISyntaxException {
		
		
		HttpEntity<?> responseEntity = ResponseEntity.EMPTY;
		String uri = "http://localhost:8081/users";
		RestTemplate restTemplate = new RestTemplate();
		try{
			responseEntity = restTemplate.postForEntity(uri, user , UserDto.class);
		}catch(HttpClientErrorException httpEx){
			httpEx.printStackTrace();
		}catch(Exception ex) {
			if(ex instanceof ResourceAccessException) {
				System.out.println(ex.getMessage());
			}
			ex.printStackTrace();
		}
		return responseEntity;
	}
	public String getUser() {
		String uri = "http://localhost:8080/user-mngmt/users/{email}";
		
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setMessageConverters(new MessageConverter().getMessageConverters());
		
		Map<String, String> param = new HashMap<String, String>();
		param.put("email", "Rajanmishra@gmail.com");
		
		ResponseEntity<UserDto> responseEntity = restTemplate.getForEntity(uri, UserDto.class, param);
		ResponseEntity<String> res = restTemplate.postForEntity(uri, responseEntity.getBody(), String.class);
		return res.getBody();
	}
}
