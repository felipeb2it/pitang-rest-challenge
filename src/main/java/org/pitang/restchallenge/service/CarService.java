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
    
    /**
     * Cria um novo carro no banco de dados.
     * 
     * @param carDto A entidade Car a ser criada.
     * @param user O usuário ao qual o carro pertence.
     * @return CarEntity A entidade Car criada.
     */
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
    
    /**
     * Remove um carro do banco de dados.
     * 
     * @param id O ID do carro a ser removido.
     * @throws EntityNotFoundException Se nenhum carro com o ID fornecido for encontrado.
     */
    public void deleteCar(Long id) {
    	carRepository.deleteById(id);
    }
    
    /**
     * Atualiza os detalhes de um carro existente.
     * 
     * @param id O ID do carro a ser atualizado.
     * @param carDto Detalhes do carro para atualização.
     * @return Optional A entidade Car atualizada caso exista.
     * @throws EntityNotFoundException Se nenhum carro com o ID fornecido for encontrado.
     */
    public Optional<CarEntity> updateCar(Long id, CarDTO carDto) {
        return carRepository.findById(id).map(car -> {
        	Optional.ofNullable(carDto.color()).ifPresent(car::setColor);
        	Optional.ofNullable(carDto.licensePlate()).ifPresent(car::setLicensePlate);
        	Optional.ofNullable(carDto.model()).ifPresent(car::setModel);
        	Optional.ofNullable(carDto.year()).ifPresent(car::setYear);
        	return carRepository.save(car);
        });
    }
    

}
