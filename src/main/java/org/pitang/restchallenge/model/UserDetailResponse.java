package org.pitang.restchallenge.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"firstName", "lastName", "email", "birthday", "phone", "login", "password", "createdAt", "lastLogin", "cars"})
public interface UserDetailResponse {
	
	String getFirstName();
	String getLastName();
	String getEmail();
	LocalDate getBirthday();
	String getPhone();
	String getLogin();
	String getPassword();
	List<CarEntity> getCars();
	LocalDate getCreatedAt();
	LocalDateTime getLastLogin();
}