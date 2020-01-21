package com.user.dao;

import java.io.Serializable;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.user.dao.repository.UserRepository;
import com.user.exception.EntityPersistanceException;
import com.user.model.User;
@Repository
@Transactional
public class UserDaoImpl implements UserDao {
	Logger logger = Logger.getLogger(UserDaoImpl.class.getName());
	
	@Autowired
	//private SessionFactory sessionFactory;
	
	private EntityManager entityManager;
	@Autowired
	private UserRepository uesrRepository;

	@Override
	@Transactional(propagation= Propagation.REQUIRES_NEW, rollbackFor = EntityPersistanceException.class)
	public Serializable save(User user) throws EntityPersistanceException {
		Transaction transaction = null;
		try {
			Session session = entityManager.unwrap(Session.class);
			Serializable id = (String)session.save(user);
			session.close();
			return id;
		}catch(Exception e) {
			throw new EntityPersistanceException(user.getEmail());
		} finally {
			if(transaction!=null && transaction.isActive()&& !transaction.getRollbackOnly()) {
				transaction.commit();
			}
		}
	}
	@Override
	public User getByUsername(String username)throws EntityNotFoundException {
		Example<User> example  = Example.of(new User(username));
		try {
			return uesrRepository.findOne(example).get();
		}catch(Exception e) {
			//e.printStackTrace();
			throw new EntityNotFoundException("Username: "+username+" not present");
		}
	}
}
