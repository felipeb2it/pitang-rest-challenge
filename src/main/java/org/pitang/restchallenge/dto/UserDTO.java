package org.pitang.restchallenge.dto;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.NotBlank;

public record UserDTO(Long id, @NotBlank String firstName, @NotBlank String lastName, @NotBlank String email, LocalDate birthday, @NotBlank String phone,
		@NotBlank String login, @NotBlank String password, List<CarDTO> cars) {

}
