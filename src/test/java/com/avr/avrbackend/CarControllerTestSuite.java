package com.avr.avrbackend;

import com.avr.avrbackend.cars.controller.CarController;
import com.avr.avrbackend.cars.controller.CarNotFoundExeption;
import com.avr.avrbackend.cars.domain.Car;
import com.avr.avrbackend.cars.domain.CarDto;
import com.avr.avrbackend.cars.mapper.CarMapper;
import com.avr.avrbackend.cars.service.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class CarControllerTestSuite {

    @Mock
    private CarService carService;

    @Mock
    private CarMapper carMapper;

    @InjectMocks
    private CarController carController;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldGetListOfCars() {
        // Given
        Car car = new Car();
        car.setCarId(1L);
        CarDto carDto = new CarDto();
        carDto.setCarId(1L);

        when(carService.getAllCars()).thenReturn(Collections.singletonList(car));
        when(carMapper.mapToCarDto(car)).thenReturn(carDto);

        // When
        ResponseEntity<List<CarDto>> response = carController.getListOfCars();

        // Then
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        verify(carService, times(1)).getAllCars();
        verify(carMapper, times(1)).mapToCarDto(any(Car.class));
    }

    @Test
    void shouldGetCarById() {
        // Given
        Car car = new Car();
        car.setCarId(1L);
        CarDto carDto = new CarDto();
        carDto.setCarId(1L);

        when(carService.getCarById(1L)).thenReturn(Optional.of(car));
        when(carMapper.mapToCarDto(car)).thenReturn(carDto);

        // When
        ResponseEntity<CarDto> response = carController.getCat(1L);

        // Then
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(carDto, response.getBody());
        verify(carService, times(1)).getCarById(1L);
        verify(carMapper, times(1)).mapToCarDto(car);
    }

//    @Test
//    void shouldCreateCar() {
//        // Given
//        CarDto carDto = new CarDto();
//        carDto.setCarId(1L);
//        Car car = new Car();
//        car.setCarId(1L);
//
//        when(carMapper.mapToCar(carDto)).thenReturn(car);
//        doNothing().when(carService).createCar(car);
//
//        // When
//        ResponseEntity<Void> response = carController.createCar(carDto);
//
//        // Then
//        assertNotNull(response);
//        assertEquals(200, response.getStatusCodeValue());
//        verify(carMapper, times(1)).mapToCar(carDto);
//        verify(carService, times(1)).createCar(car);
//    }

    @Test
    void shouldUpdateCar() {
        // Given
        CarDto carDto = new CarDto();
        carDto.setCarId(1L);
        Car existingCar = new Car();
        existingCar.setCarId(1L);
        Car updatedCar = new Car();
        updatedCar.setCarId(1L);

        when(carService.getCarById(1L)).thenReturn(Optional.of(existingCar));
        when(carMapper.mapToCar(carDto)).thenReturn(updatedCar);
        when(carService.updateCar(updatedCar)).thenReturn(updatedCar);
        when(carMapper.mapToCarDto(updatedCar)).thenReturn(carDto);

        // When
        ResponseEntity<CarDto> response = carController.updateCar(1L, carDto);

        // Then
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(carDto, response.getBody());
        verify(carService, times(1)).getCarById(1L);
        verify(carMapper, times(1)).mapToCar(carDto);
        verify(carService, times(1)).updateCar(updatedCar);
    }

    @Test
    void shouldDeleteCar() {
        // Given
        doNothing().when(carService).deleteCar(1L);

        // When
        ResponseEntity<Void> response = carController.deleteCar(1L);

        // Then
        assertNotNull(response);
        assertEquals(204, response.getStatusCodeValue());
        verify(carService, times(1)).deleteCar(1L);
    }

    @Test
    void shouldLockCar() throws CarNotFoundExeption {
        // Given
        doNothing().when(carService).lockCar(1L);

        // When
        ResponseEntity<Void> response = carController.lockCar(1L);

        // Then
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        verify(carService, times(1)).lockCar(1L);
    }

    @Test
    void shouldUnlockCar() throws CarNotFoundExeption {
        // Given
        doNothing().when(carService).unlockCar(1L);

        // When
        ResponseEntity<Void> response = carController.unlockCar(1L);

        // Then
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        verify(carService, times(1)).unlockCar(1L);
    }

    @Test
    void shouldHandleLockCarNotFoundException() throws CarNotFoundExeption {
        // Given
        doThrow(new CarNotFoundExeption()).when(carService).lockCar(1L);

        // When
        ResponseEntity<Void> response = carController.lockCar(1L);

        // Then
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        verify(carService, times(1)).lockCar(1L);
    }

    @Test
    void shouldHandleUnlockCarNotFoundExeption() throws CarNotFoundExeption {
        // Given
        doThrow(new CarNotFoundExeption()).when(carService).unlockCar(1L);

        // When
        ResponseEntity<Void> response = carController.unlockCar(1L);

        // Then
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        verify(carService, times(1)).unlockCar(1L);
    }

    @Test
    void shouldAssignUserToCar() {
        // Given
        Car car = new Car();
        car.setCarId(1L);

        when(carService.assignUserToCar(1L, 1L)).thenReturn(car);

        // When
        Car response = carController.assignUserToCar(1L, 1L);

        // Then
        assertNotNull(response);
        assertEquals(1L, response.getCarId());
        verify(carService, times(1)).assignUserToCar(1L, 1L);
    }

}
