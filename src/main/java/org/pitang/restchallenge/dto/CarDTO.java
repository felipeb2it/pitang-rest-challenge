package org.pitang.restchallenge.dto;

public record CarDTO(Long id, int year, String licensePlate, String model, String color, UserDTO user) {

}
