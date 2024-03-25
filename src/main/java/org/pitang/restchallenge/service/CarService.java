package org.pitang.restchallenge.service;

import java.util.List;
import java.util.Optional;

import org.pitang.restchallenge.dto.CarDTO;
import org.pitang.restchallenge.model.CarEntity;
import org.pitang.restchallenge.model.UserEntity;
import org.pitang.restchallenge.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarService {
	
	@Autowired
	private CarRepository carRepository;
	
    /**
     * Lista todos os carros presentes no banco de dados.
     * 
     * @return Uma lista de entidades Car.
     */
    public List<CarEntity> findAllCars() {
        return carRepository.findAll();
    }
    
    public CarEntity createCar(CarDTO carDto, UserEntity user) {
    	CarEntity car = new CarEntity();
    	car.setColor(carDto.color());
    	car.setLicensePlate(carDto.licensePlate());
    	car.setModel(carDto.model());
    	car.setYear(carDto.year());
    	car.setUser(user);
    	
    	return carRepository.save(car);
    }
    
    public Optional<CarEntity> findCarById(Long id) {
        return carRepository.findById(id);
    }
    
    public void deleteUser(Long id) {
    	carRepository.deleteById(id);
    }
    
    public Optional<CarEntity> updateUser(Long id, CarDTO carDto) {
        return carRepository.findById(id).map(car -> {
        	Optional.ofNullable(carDto.color()).ifPresent(car::setColor);
        	Optional.ofNullable(carDto.licensePlate()).ifPresent(car::setLicensePlate);
        	Optional.ofNullable(carDto.model()).ifPresent(car::setModel);
        	Optional.ofNullable(carDto.year()).ifPresent(car::setYear);
        	return carRepository.save(car);
        });
    }
    

}
