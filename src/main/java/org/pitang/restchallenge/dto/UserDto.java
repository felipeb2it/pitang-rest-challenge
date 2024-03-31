package org.pitang.restchallenge.dto;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import jakarta.validation.constraints.NotBlank;

public record UserDto(@NotBlank String firstName, @NotBlank String lastName, @NotBlank String email,
		@JsonSerialize(using = LocalDateSerializer.class) LocalDate birthday, @NotBlank String phone,
		@NotBlank String login, @NotBlank String password, List<CarDto> cars) {

}
