package com.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.user.service.RoleService;

@RestController
@RequestMapping("/role")
public class RoleController {

	@Autowired
	private RoleService roleService;
	
	@GetMapping("/default")
	public String getDefaultRole() {
		try {
			return roleService.getDefaultRole().getName();	
		}catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Can't perform action.", e);
		}
		
		
	}
}
