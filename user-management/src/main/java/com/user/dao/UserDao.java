package com.user.dao;

import java.io.Serializable;

import javax.persistence.EntityNotFoundException;

import com.user.exception.EntityPersistanceException;
import com.user.model.User;

public interface UserDao {
	public Serializable save(User user) throws EntityPersistanceException;
	public User getByUsername(String email) throws EntityNotFoundException;
}
