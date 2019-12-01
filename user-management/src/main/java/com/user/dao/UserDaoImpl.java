package com.user.dao;

import java.util.logging.Logger;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.user.exception.EntityPersistanceException;
import com.user.model.User;
@Repository
@Transactional
public class UserDaoImpl implements UserDao {
	Logger logger = Logger.getLogger(UserDaoImpl.class.getName());
	
	@Autowired
	//private SessionFactory sessionFactory;
	private EntityManager entityManager;

	@Override
	@Transactional(propagation= Propagation.REQUIRES_NEW, rollbackFor = EntityPersistanceException.class)
	public User save(User user) throws EntityPersistanceException {
		Transaction transaction = null;
		try {
			Session session = entityManager.unwrap(Session.class);
			//Session session = sessionFactory.getCurrentSession();
			//transaction = session.beginTransaction();
			String result = (String)session.save(user);
			session.close();
			return new User();
		}catch(Exception e) {
			throw new EntityPersistanceException(user.getEmail());
		} finally {
			if(transaction!=null && transaction.isActive()&& !transaction.getRollbackOnly()) {
				transaction.commit();
			}
		}
	}

	@Override
	public User getByCredential(String username, String password) {
		// TODO Auto-generated method stub
		return null;
	}
}
