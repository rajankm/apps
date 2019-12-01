package com.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user.dao.UserDao;
import com.user.exception.EntityPersistanceException;
import com.user.model.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao; 
	
	@Override
	public User save(User user) throws EntityPersistanceException {
		return userDao.save(user);
	}

	@Override
	public User getByCredential(String username, String password) {
		// TODO Auto-generated method stub
		return null;
	}

}
