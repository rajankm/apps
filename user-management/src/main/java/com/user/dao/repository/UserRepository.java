package com.user.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import com.user.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, QueryByExampleExecutor<User> {

}
