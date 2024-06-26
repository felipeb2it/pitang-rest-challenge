package org.pitang.restchallenge.controller;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.pitang.restchallenge.dto.CarDTO;
import org.pitang.restchallenge.dto.UserDetailResponse;
import org.pitang.restchallenge.model.CarEntity;
import org.pitang.restchallenge.service.CarService;
import org.pitang.restchallenge.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
	
    /**
     * Endpoint para listar todos os carros.
     * 
     * @return Uma lista de carros.
     */
    @GetMapping("/cars")
    public ResponseEntity<List<CarEntity>> cars() {
    	return ResponseEntity.ok(carService.findAllCars());
    }
    
    
    /**
     * Endpoint para criar um novo carro.
     * 
     * @param carDto O carro a ser criado.
     * @param authentication Dados de autenticação
     * @return ResponseEntity do carro criado.
     */
    @PostMapping("/cars")
    public ResponseEntity<CarEntity> createCar(@RequestBody CarDTO carDto, Authentication authentication){
    	String login = authentication.getName();
    	var user = userService.findUserByLogin(login);
    	return ResponseEntity.ok(carService.createCar(carDto, user.get()));
    }
    
    @GetMapping("/cars/{id}")
    public ResponseEntity<CarEntity> findCarById(@PathVariable("id") long id) {
    	var carOptional = carService.findCarById(id);
    	
    	return carOptional.map(ResponseEntity::ok)
                 .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    /**
     * Endpoint para deletar um carro.
     * 
     * @param id do carro a ser deletado.
	 * @return BodyBuilder Uma resposta vazia com status HTTP OK se o carro for deletado com sucesso.
	*/
    @DeleteMapping("/cars/{id}")
    public ResponseEntity<?> deleteCar(@PathVariable("id") long id) {
    	carService.deleteCar(id);
    	return ResponseEntity.ok().build();
    }
    
    /**
     * Endpoint para atualizar um carro existente.
     * 
     * @param id do carro a ser atualizado.
     * @param carDto com novos detalhes do carro.
     * @return ResponseEntity do carro atualizado.
     */
    @PutMapping("/cars/{id}")
    public ResponseEntity<CarEntity> updateCar(@PathVariable Long id, @RequestBody CarDTO carDto) {
    	var updatedCar =  carService.updateCar(id, carDto);
    	return updatedCar.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    
    @GetMapping("/cars/bonusOrder")
    public ResponseEntity<List<CarEntity>> carsBonusOrdered() {
    	List<CarEntity> allCars = carService.findAllCars();
    	
    	List<CarEntity> sortedCars = allCars.stream()
    	        .sorted(Comparator.comparingInt(CarEntity::getUseCount).reversed()
    	                .thenComparing(CarEntity::getModel))
    	        .collect(Collectors.toList());
    	
    	return ResponseEntity.ok(sortedCars);
    }

}
