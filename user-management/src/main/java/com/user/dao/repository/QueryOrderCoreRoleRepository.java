package com.user.dao.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class QueryOrderCoreRoleRepository {
	Logger log = LoggerFactory.getLogger(getClass());
	@PersistenceContext
	@Qualifier("primaryEntityManagerFactory")
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public String executeQuery(int todoID) {
		String roleName = (String) entityManager.createNativeQuery(
				"SELECT name FROM role WHERE id = ?1")
				.setParameter(1, todoID)
				.getResultList()
				.stream()
				.findFirst()
				.orElse(null);
		log.debug("After native query Todo ID: "+ todoID +" / Role Name: "+roleName);
		return roleName;
	}

}
