package com.user.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import com.user.exception.BadCredentialsException;
import com.user.exception.EntityPersistanceException;
import com.user.model.User;

@Service
public interface UserService {
	public User save(User user)throws EntityPersistanceException;
	public User getByUsername(String email) throws EntityNotFoundException;
	public User getByCredential(String username, String password) throws BadCredentialsException;
}
