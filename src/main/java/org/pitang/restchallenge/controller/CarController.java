package org.pitang.restchallenge.controller;

import java.util.List;

import org.pitang.restchallenge.dto.CarDTO;
import org.pitang.restchallenge.model.CarEntity;
import org.pitang.restchallenge.model.UserDetailResponse;
import org.pitang.restchallenge.service.CarService;
import org.pitang.restchallenge.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CarController {
	
	
	@Autowired
	private CarService carService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/me")
	public ResponseEntity<UserDetailResponse> user(Authentication authentication) {
		String login = authentication.getName();
    	return ResponseEntity.ok(userService.findProjectedUserByLogin(login).get());
    }
	
	
    @GetMapping("/cars")
    public ResponseEntity<List<CarEntity>> cars() {
    	return ResponseEntity.ok(carService.findAllCars());
    }
    
    @PostMapping("/cars")
    public ResponseEntity<CarEntity> createCar(@RequestBody CarDTO userDto, Authentication authentication){
    	String login = authentication.getName();
    	var user = userService.findUserByLogin(login);
    	return ResponseEntity.ok(carService.createCar(userDto, user.get()));
    }
    
    @GetMapping("/cars/{id}")
    public ResponseEntity<CarEntity> findCarById(@PathVariable("id") long id) {
    	var carOptional = carService.findCarById(id);
    	
    	return carOptional.map(ResponseEntity::ok)
                 .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/cars/{id}")
    public BodyBuilder removeCar(@PathVariable("id") long id) {
    	carService.deleteUser(id);
    	return ResponseEntity.ok();
    }
    
    @PutMapping("/cars/{id}")
    public ResponseEntity<CarEntity> updateCar(@PathVariable Long id, @RequestBody CarDTO carDto) {
    	var updatedCar =  carService.updateUser(id, carDto);
    	return updatedCar.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
