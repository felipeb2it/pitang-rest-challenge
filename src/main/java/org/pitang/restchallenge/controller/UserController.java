package org.pitang.restchallenge.controller;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

import org.pitang.restchallenge.dto.ErrorMessagesDto;
import org.pitang.restchallenge.dto.LoginRequestDto;
import org.pitang.restchallenge.dto.UserDTO;
import org.pitang.restchallenge.model.CarEntity;
import org.pitang.restchallenge.model.UserEntity;
import org.pitang.restchallenge.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.Valid;


/**
 * Controller para operações relacionadas a usuários.
 * Provê endpoints para CRUD de carros no sistema.
 */
@RestController
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	record JwtResponse(String token) {}
	
	@Value("${pitang.jwt.secret}")
	private String secretKey;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequestDto loginRequest) {
    	boolean validUser = userService.findUserByLoginAndCheckPass(loginRequest);
    	if(validUser) {
    		return ResponseEntity.ok().body(new JwtResponse(generateJwtToken(loginRequest.login())));
    	} else {
    		ErrorMessagesDto error = new ErrorMessagesDto("Invalid login or password", HttpStatus.NOT_FOUND.value());
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    	}
        
    }
    
    public String generateJwtToken(String username) {
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());

        String jwt = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + 3600000))
                .signWith(key)
                .compact();

        return jwt;
    }
	
    /**
     * Endpoint para listar todos os usuários cadastrados no sistema.
     * 
     * @return Uma ResponseEntity contendo uma lista de usuários e o status HTTP OK.
     */
    @GetMapping("/users")
    public ResponseEntity<List<UserEntity>> users() {
    	return ResponseEntity.ok(userService.findAllUsers());
    }
    
    /**
     * Cria um novo usuário no sistema.
     * 
     * @param userDto Objeto DTO contendo informações do usuario a ser criado.
     * @return ResponseEntity com o usuário criado e o status HTTP.
     */
    @PostMapping("/users")
    public ResponseEntity<?> createUser(@RequestBody @Valid UserDTO userDto){
    	if(userService.findByLoginAndCheck(userDto.login())) {
    		ErrorMessagesDto error = new ErrorMessagesDto("Login already exists", HttpStatus.BAD_REQUEST.value());
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    	} else if(userService.findByEmailAndCheck(userDto.email())) {
    		ErrorMessagesDto error = new ErrorMessagesDto("E-mail already exists", HttpStatus.BAD_REQUEST.value());
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    	}
    	return ResponseEntity.ok(userService.createUser(userDto));
    }
    
    @GetMapping("/users/{id}")
    public ResponseEntity<UserEntity> findUserById(@PathVariable("id") long id) {
    	var userOptional = userService.findUserById(id);
    	
    	return userOptional.map(ResponseEntity::ok)
                 .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    /**
     * Endpoint para deletar um usuário pelo ID.
     *
     * @param id O ID do usuário a ser deletado.
     * @return ResponseEntity com status OK se o usuário for deletado com sucesso.
     */
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {
    	userService.deleteUser(id);
    	return ResponseEntity.ok().build();
    }
    
    
    /**
     * Atualiza dados do usuário no sistema.
     * 
     * @param id Objeto do usuário a ser atualizado.
     * @param userDto Objeto do usuário a ser atualizado.
     * @return ResponseEntity com o usuário atualizado e o status HTTP.
     */
    @PutMapping("/users/{id}")
    public ResponseEntity<UserEntity> updateUser(@PathVariable Long id, @RequestBody UserDTO userDto) {
    	var updatedUser =  userService.updateUser(id, userDto);
    	return updatedUser.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    
    @GetMapping("/users/bonusOrder")
    public ResponseEntity<List<UserEntity>> usersBonusOrdered() {
    	List<UserEntity> users = userService.findAllUsers();
    	
    	users.sort(Comparator
    	        .comparingInt((UserEntity u) -> u.getCars() != null ? u.getCars().stream().mapToInt(CarEntity::getUseCount).sum() : 0)
    	        .reversed()
    	        .thenComparing(UserEntity::getLogin));
    	List<UserEntity> sortedUsers = users;
    	
    	return ResponseEntity.ok(sortedUsers);
    }
   

    

}
