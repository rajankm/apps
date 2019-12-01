package com.user.service;

import org.springframework.stereotype.Service;

import com.user.exception.EntityNotFoundException;
import com.user.exception.EntityPersistanceException;
import com.user.model.User;

@Service
public interface UserService {
	public User save(User user)throws EntityPersistanceException;
	public User getByCredential(String username, String password)throws EntityNotFoundException;
}
