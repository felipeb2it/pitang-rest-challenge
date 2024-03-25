package org.pitang.restchallenge.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.pitang.restchallenge.dto.CarDTO;
import org.pitang.restchallenge.dto.LoginRequestDto;
import org.pitang.restchallenge.dto.UserDTO;
import org.pitang.restchallenge.model.CarEntity;
import org.pitang.restchallenge.model.UserDetailResponse;
import org.pitang.restchallenge.model.UserEntity;
import org.pitang.restchallenge.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Serviço para gerenciamento de operações de usuários.
 * Encapsula a lógica de negócio relacionada a usuários.
 */
@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
    public List<UserEntity> findAllUsers() {
        return userRepository.findAll();
    }
    
    
    /**
     * Cria um novo usuário no repositório.
     * 
     * @param userDto Objeto usuário a ser persistido.
     * @return O usuário persistido.
     */
    public UserEntity createUser(UserDTO userDto) {
    	UserEntity user = new UserEntity();
    	user.setFirstName(userDto.firstName());
    	user.setLastName(userDto.lastName());
    	user.setBirthday(userDto.birthday());
    	user.setEmail(userDto.email());
    	user.setLogin(userDto.login());
    	user.setPassword(userDto.password());
    	user.setPhone(userDto.phone());
    	
    	if(userDto.cars() != null && !userDto.cars().isEmpty()) {
    		List<CarEntity> cars = new ArrayList<>();
    		for(CarDTO carDto : userDto.cars()) {
    			CarEntity car = new CarEntity();
    			car.setColor(carDto.color());
    			car.setLicensePlate(carDto.licensePlate());
    			car.setModel(carDto.model());
    			car.setYear(carDto.year());
    			car.setUser(user);
    			cars.add(car);
    		}
    		user.setCars(cars);
    	}
    	
    	return userRepository.save(user);
    }
    
    public Optional<UserEntity> findUserById(Long id) {
        return userRepository.findById(id);
    }
    
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    
    public Optional<UserEntity> updateUser(Long id, UserDTO userDto) {
        return userRepository.findById(id).map(user -> {
        	Optional.ofNullable(userDto.firstName()).ifPresent(user::setFirstName);
        	Optional.ofNullable(userDto.lastName()).ifPresent(user::setLastName);
        	Optional.ofNullable(userDto.birthday()).ifPresent(user::setBirthday);
        	Optional.ofNullable(userDto.email()).ifPresent(user::setEmail);
        	Optional.ofNullable(userDto.login()).ifPresent(user::setLogin);
        	Optional.ofNullable(userDto.password()).ifPresent(user::setPassword);
        	Optional.ofNullable(userDto.phone()).ifPresent(user::setPhone);
        	
        	Optional.ofNullable(userDto.phone()).ifPresent(user::setPhone);
        	Optional.ofNullable(userDto.phone()).ifPresent(user::setPhone);
            return userRepository.save(user);
        });
    }
    
    public Optional<UserEntity> findUserByLogin(String login) {
    	return userRepository.findByLogin(login);
    }
    
    public Optional<UserDetailResponse> findProjectedUserByLogin(String login) {
    	return userRepository.findProjectedByLogin(login);
    }
    
    public boolean findUserByLoginAndCheckPass(LoginRequestDto login) {
    	return userRepository.findByLogin(login.login()).map(user -> {
    		user.setLastLogin(LocalDateTime.now());
    		userRepository.save(user);
    		return user.getPassword().equals(login.password())? true : false; 
    	}).orElse(false);
    }
    
    public boolean findByEmailAndCheck(String email) {
    	return userRepository.findByEmail(email).isPresent();
    }
    
    public boolean findByLoginAndCheck(String login) {
    	return userRepository.findByLogin(login).isPresent();
    }
    

}
