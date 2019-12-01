package com.app.controller;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.app.config.MessageConverter;
import com.app.entity.dto.UserDto;

@Controller
@RequestMapping("/usr")
public class UserController {
	
	
	@RequestMapping("/signinup")
	public ModelAndView showSigninupForm(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.addObject(new UserDto());
		mv.setViewName("signinup");
		return mv;
	}

	@PostMapping(path= {"/users"})
	@ResponseBody
	public HttpEntity<?> addUser(@ModelAttribute("user") UserDto user, BindingResult result) throws URISyntaxException {
		
		
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
	@GetMapping(path= {"/",""})
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
