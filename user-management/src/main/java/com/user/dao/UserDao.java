package com.user.dao;

import java.io.Serializable;

import com.user.exception.EntityNotFoundException;
import com.user.exception.EntityPersistanceException;
import com.user.model.User;

public interface UserDao {
	public Serializable save(User user) throws EntityPersistanceException;
	public User getByCredential(String username, String password)throws EntityNotFoundException;
	public User findByUsername(String email);
}
