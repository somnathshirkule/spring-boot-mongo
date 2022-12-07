package com.somnathshirkule.mongoassessment.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.somnathshirkule.mongoassessment.models.User;

public interface UserRepository extends MongoRepository<User, String>{
	public User findByEmail(String email);
}
