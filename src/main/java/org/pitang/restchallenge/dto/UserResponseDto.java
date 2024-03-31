package org.pitang.restchallenge.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.pitang.restchallenge.model.CarEntity;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"firstName", "lastName", "email", "birthday", "phone", "login", "password", "createdAt", "lastLogin", "cars"})
public interface UserResponseDto {
	
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