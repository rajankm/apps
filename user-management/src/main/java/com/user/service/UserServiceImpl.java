package com.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.user.dao.UserDao;
import com.user.exception.EntityPersistanceException;
import com.user.model.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao; 
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Override
	public User save(User user) throws EntityPersistanceException {
		user.setPassword(encoder.encode(user.getPassword()));
		
		String id =(String) userDao.save(user);
		user.setId(id);
		return user;
	}
	@Override
	public User findByUsername(String email) {
		return userDao.findByUsername(email);
	}

	@Override
	public User getByCredential(String username, String password) {
		return null;
	}

}
