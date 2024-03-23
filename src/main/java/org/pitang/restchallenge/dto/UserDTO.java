package org.pitang.restchallenge.dto;

import java.time.LocalDate;
import java.util.List;

public record UserDTO(Long id, String firstName, String lastName, String email, LocalDate birthday, String phone, String login, String password, List<CarDTO> cars) {
	
}
