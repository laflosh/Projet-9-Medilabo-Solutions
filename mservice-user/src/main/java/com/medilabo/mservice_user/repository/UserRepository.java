package com.medilabo.mservice_user.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.medilabo.mservice_user.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

	public User findUserByMail(String mail);
	
	public User findUserByUsername(String username);
	
}
