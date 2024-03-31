package org.pitang.restchallenge.controller;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.pitang.restchallenge.dto.CarDto;
import org.pitang.restchallenge.model.CarEntity;
import org.pitang.restchallenge.service.CarService;
import org.pitang.restchallenge.service.UserService;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
public class CarControllerTest {
	
    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private CarService carService;

    @Mock
    private UserService userService;

    @InjectMocks
    private CarController carController;

    @BeforeEach
    public void setup() {
        mockMvc = standaloneSetup(carController).build();
    }
    
    @Test
    public void whenCars_thenReturnCarList() throws Exception {
        List<CarEntity> carList = List.of(new CarEntity(), new CarEntity());
        given(carService.findAllCars()).willReturn(carList);

        mockMvc.perform(get("/api/cars"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(carList.size()));
    }
    
    @Test
    public void whenFindCarById_thenReturnCar() throws Exception {
        long carId = 1L;
        CarEntity carEntity = new CarEntity(); // Inicialize conforme necessário
        given(carService.findCarById(carId)).willReturn(Optional.of(carEntity));

        mockMvc.perform(get("/api/cars/{id}", carId))
                .andExpect(status().isOk());
    }
    
    @Test
    public void whenDeleteCar_thenCarShouldBeDeleted() throws Exception {
        long carId = 1L;
        doNothing().when(carService).deleteCar(carId);

        mockMvc.perform(delete("/api/cars/{id}", carId))
                .andExpect(status().isOk());
    }
    
    @Test
    public void whenUpdateCar_thenReturnUpdatedCar() throws Exception {
        long carId = 1L;
        CarDto carDto = new CarDto(1999, "abc", "abc", "abc", null);
        CarEntity updatedCarEntity = new CarEntity(/* inicialize conforme necessário */);
        given(carService.updateCar(carId, carDto)).willReturn(Optional.of(updatedCarEntity));

        mockMvc.perform(put("/api/cars/{id}", carId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(carDto)))
                .andExpect(status().isOk());
    }
  
}
