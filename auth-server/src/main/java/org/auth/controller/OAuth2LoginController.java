package com.auth.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class OAuth2LoginController extends GenericController {
	
	@GetMapping("/oauth2")
	public String login() {
		return "login";
	}

	@GetMapping("/oauth2/code/{registrationId}")
	public String callback(@AuthenticationPrincipal OAuth2User principal, Model model) {
		model.addAttribute("userName", principal.getName());
		model.addAttribute("userAttributes", principal.getAttributes());
		return model.toString();
	}

	@GetMapping("/oauth2/error")
	public String error(HttpServletRequest request) {
		String message = (String) request.getSession().getAttribute("error.message");
		request.getSession().removeAttribute("error.message");
		return message;
	}

}
