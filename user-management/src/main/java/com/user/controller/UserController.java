package com.user.controller;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.user.exception.EntityPersistanceException;
import com.user.model.Role;
import com.user.model.User;
import com.user.service.RoleService;
import com.user.service.UserService;

@RestController
@RequestMapping(path= {"/user"})
public class UserController extends GenericController{
	
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public User createUser(@RequestBody User user) {
		try {
			if(user.getRole()==null) {
				Role role = roleService.getDefaultRole();
				user.setRole(role);
			}
			return userService.save(user);	
		}catch(EntityPersistanceException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Can't perform action.", e);
		}
	}
	@GetMapping("/{username}")
	@ResponseStatus(HttpStatus.FOUND)
	public User getUser(@PathVariable(required = true) String username) {
		try {
			return userService.getByUsername(username);
		}catch(EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Can't perform action.", e);
		}
	}
}
