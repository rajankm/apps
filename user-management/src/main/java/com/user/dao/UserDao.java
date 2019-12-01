package com.user.dao;

import com.user.exception.EntityNotFoundException;
import com.user.exception.EntityPersistanceException;
import com.user.model.User;

public interface UserDao {
	public User save(User user) throws EntityPersistanceException;
	public User getByCredential(String username, String password)throws EntityNotFoundException;
}
