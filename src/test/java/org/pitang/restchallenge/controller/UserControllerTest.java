package org.pitang.restchallenge.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.pitang.restchallenge.dto.LoginRequestDto;
import org.pitang.restchallenge.dto.UserDTO;
import org.pitang.restchallenge.model.UserEntity;
import org.pitang.restchallenge.service.UserService;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(properties = "pitang.jwt.secret=secretKeysecretKeysecretKeysecretKey")
public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController carSysController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = standaloneSetup(carSysController).build();
        ReflectionTestUtils.setField(carSysController, "secretKey", "secretKeysecretKeysecretKeysecretKey");
    }
    
    @Test
    void whenAuthenticateUser_thenReturnJwtToken() throws Exception {
        LoginRequestDto loginRequest = new LoginRequestDto("user", "password");
        given(userService.findUserByLoginAndCheckPass(loginRequest)).willReturn(true);

        mockMvc.perform(post("/api/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());
    }
    
    @Test
    void whenListUsers_thenReturnUserList() throws Exception {
        List<UserEntity> users = List.of(new UserEntity(), new UserEntity());
        given(userService.findAllUsers()).willReturn(users);

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(users.size()));
    }
    
    @Test
    void whenCreateUser_thenReturnCreatedUser() throws Exception {
        UserDTO newUser = new UserDTO("newUser", "Oliveira", "newuser@example.com", LocalDate.of(1983, 10, 8), "6654654", "felipe", "felpcx", null);
        UserEntity savedUser = new UserEntity();

        given(userService.createUser(newUser)).willReturn(savedUser);
        given(userService.findByLoginAndCheck(newUser.login())).willReturn(false);
        given(userService.findByEmailAndCheck(newUser.email())).willReturn(false);

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isOk());
    }
    
    @Test
    void whenFindUserById_thenReturnUser() throws Exception {
        long userId = 1L;
        UserEntity userEntity = new UserEntity(); // Suponha que UserEntity é apropriadamente instanciado
        given(userService.findUserById(userId)).willReturn(Optional.of(userEntity));

        mockMvc.perform(get("/api/users/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());
    }

    @Test
    void whenFindUserById_thenNotFound() throws Exception {
        long userId = 2L;
        given(userService.findUserById(userId)).willReturn(Optional.empty());

        mockMvc.perform(get("/api/users/{id}", userId))
                .andExpect(status().isNotFound());
    }
    
    @Test
    void whenDeleteUser_thenUserShouldBeDeleted() throws Exception {
        long userId = 1L;
        doNothing().when(userService).deleteUser(userId);

        mockMvc.perform(delete("/api/users/{id}", userId))
                .andExpect(status().isOk());

        verify(userService, times(1)).deleteUser(userId);
    }
    
    @Test
    void whenUpdateUser_thenUserShouldBeUpdated() throws Exception {
        long userId = 1L;
        UserDTO userDto = new UserDTO("newUser", "Oliveira", "newuser@example.com", LocalDate.of(1983, 10, 8), "6654654", "felipe", "felpcx", null);
        UserEntity updatedUserEntity = new UserEntity(); // Suponha que UserEntity é apropriadamente instanciado

        given(userService.updateUser(eq(userId), any())).willReturn(Optional.of(updatedUserEntity));

        mockMvc.perform(put("/api/users/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk());

        verify(userService, times(1)).updateUser(eq(userId), any());
    }

    @Test
    void whenUpdateUser_thenNotFound() throws Exception {
        long userId = 2L;
        UserDTO userDto = new UserDTO("newUser", "Oliveira", "newuser@example.com", LocalDate.of(1983, 10, 8), "6654654", "felipe", "felpcx", null);

        given(userService.updateUser(eq(userId), any())).willReturn(Optional.empty());

        mockMvc.perform(put("/api/users/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isNotFound());
    }

  
}
