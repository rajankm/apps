package com.user.dao;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.user.dao.repository.QueryOrderCoreRoleRepository;
import com.user.dao.repository.RoleRepository;
import com.user.model.Role;
@Repository
@Transactional
public class RoleDaoImpl implements RoleDao {

	@Autowired
	private QueryOrderCoreRoleRepository repository;
	@Autowired
	private RoleRepository roleRepo;
	@Override
	public Role getRole() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public Role getDefaultRole() throws NoResultException {
		return roleRepo.findAll().stream().filter(role-> role.isDefault()==true).findFirst().get();
	}
}
