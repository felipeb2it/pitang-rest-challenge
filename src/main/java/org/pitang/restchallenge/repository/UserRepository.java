package org.pitang.restchallenge.repository;

import java.util.Optional;

import org.pitang.restchallenge.model.UserDetailResponse;
import org.pitang.restchallenge.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

	public Optional<UserDetailResponse> findProjectedByLogin(String login);
	
	public Optional<UserEntity> findByLogin(String login);
	
	public Optional<UserDetailResponse> findByEmail(String email);
	
}
