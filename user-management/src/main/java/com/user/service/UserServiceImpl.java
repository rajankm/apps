package com.user.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.user.dao.UserDao;
import com.user.exception.BadCredentialsException;
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
	public User getByUsername(String username) throws EntityNotFoundException {
		return userDao.getByUsername(username);
	}

	@Override
	public User getByCredential(String username, String password) throws BadCredentialsException {
		password = encoder.encode(password);
		User user = userDao.getByUsername(username);
		if(user.getPassword().equals(password)) {
			return user;
		}else {
			throw new BadCredentialsException(username);
		}
	}

}
