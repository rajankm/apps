package com.app.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.app.config.MessageConverter;
import com.app.entity.dto.JSONResponse;
import com.app.entity.dto.UserDto;

@RestController
@RequestMapping("/user")
public class UserController extends GenericController {
	@Autowired
	RestTemplate restTemplate;

	@GetMapping(path = { "/login" })
	public ModelAndView showLoginForm(HttpServletRequest request, Model model) {
		ModelAndView mv = new ModelAndView();
		mv.addObject(new UserDto());
		mv.setViewName("login");
		return mv;
	}

	@GetMapping(path = { "/signup" })
	public ModelAndView showSignupForm(HttpServletRequest request, Model model) {
		ModelAndView mv = new ModelAndView();

		mv.addObject(new UserDto());
		mv.setViewName("signup");
		return mv;
	}

	@PostMapping(path = { "/signup" })
	public HttpEntity<?> addUser(@ModelAttribute("user") @Validated UserDto user) throws URISyntaxException {

		HttpEntity<?> responseEntity = ResponseEntity.EMPTY;
		try {
			responseEntity = restTemplate.postForEntity(getPropValue("url.service.user"), user, UserDto.class);
		} catch (HttpClientErrorException httpEx) {
			httpEx.printStackTrace();
		} catch (Exception ex) {
			if (ex instanceof ResourceAccessException) {
				logger.error(ex.getMessage());
			}
			ex.printStackTrace();
		}
		return responseEntity;
	}

	public String getDefaultRole() throws Exception {
		String url = "http://localhost:8761/user-management/role/default";
		try {
			ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
			return response.getBody();
		} catch (HttpClientErrorException httpEx) {
			httpEx.printStackTrace();
			throw httpEx;
		} catch (Exception ex) {
			if (ex instanceof ResourceAccessException) {
				System.out.println(ex.getMessage());
			}
			ex.printStackTrace();
			throw ex;
		}
	}

	@PostMapping(path = { "/login" })
	public JSONResponse login(@ModelAttribute UserDto user, HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		JSONResponse jsonResponse = new JSONResponse();
		//RedirectView rv = new RedirectView(request.getContextPath() + "/user/login");
		try {
			logger.debug("Calling user-authentication service.");
			ResponseEntity<UserDto> responseEntity = restTemplate.postForEntity(getPropValue("url.service.auth")+"/user", user, UserDto.class);
			logger.debug("Ressponse from user-authentication:\n \t ResponseEntity:\n\t\t StatsCode: "
					+ responseEntity.getStatusCodeValue());
			if (responseEntity.getStatusCode() == HttpStatus.OK) {
				String jwtToken = responseEntity.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
				jsonResponse.setStatus(responseEntity.getStatusCode());
				//rv = new RedirectView(request.getContextPath() + "/home?jwtToken=" + jwtToken);
				user = responseEntity.getBody();
				jsonResponse.setValue(user);
				session.setAttribute("loggedUser", user);
			} else {
				logger.info("Response received: " + responseEntity);
				//rv = new RedirectView(request.getContextPath() + "/user/login");
				jsonResponse.setValue("Invalid credentials!");
			}
		} catch (Exception e) {
			if (e instanceof ResourceAccessException) {
			}
			logger.error(e.getMessage());
			//rv = new RedirectView(request.getContextPath() + "/user/login");
			jsonResponse.setValue("Some error occured, please try after sometime.");
		}
		return jsonResponse;
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse resposne, HttpSession session) {
		String uri = "http://localhost:8082/authenticate/logout";
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(HttpHeaders.AUTHORIZATION, session.getAttribute(HttpHeaders.AUTHORIZATION).toString());
			HttpEntity<?> httpEntity = new HttpEntity<>(headers);
			ResponseEntity<String> responseEntity = restTemplate.exchange(new URI(uri), HttpMethod.GET, httpEntity,
					String.class);

		} catch (HttpClientErrorException httpEx) {
			httpEx.printStackTrace();
		} catch (Exception ex) {
			if (ex instanceof ResourceAccessException) {
				System.out.println(ex.getMessage());
			}
			ex.printStackTrace();
		}
		return "login";
	}

	public String getUser() {
		String uri = "http://localhost:8080/user-mngmt/users/{email}";

		restTemplate.setMessageConverters(new MessageConverter().getMessageConverters());

		Map<String, String> param = new HashMap<String, String>();
		param.put("email", "Rajanmishra@gmail.com");

		ResponseEntity<UserDto> responseEntity = restTemplate.getForEntity(uri, UserDto.class, param);
		ResponseEntity<String> res = restTemplate.postForEntity(uri, responseEntity.getBody(), String.class);
		return res.getBody();
	}
}
