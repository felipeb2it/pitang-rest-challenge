package org.pitang.restchallenge.repository;

import java.util.Optional;

import org.pitang.restchallenge.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

	public Optional<UserEntity> findByLogin(String login);
	
	public Optional<UserEntity> findByEmail(String email);
	
}
