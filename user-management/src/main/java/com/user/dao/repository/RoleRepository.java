package com.user.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.user.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String>{

}
