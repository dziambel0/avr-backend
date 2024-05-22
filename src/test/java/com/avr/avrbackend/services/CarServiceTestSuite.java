package com.avr.avrbackend.services;

import com.avr.avrbackend.cars.controller.CarNotFoundExeption;
import com.avr.avrbackend.cars.domain.Car;
import com.avr.avrbackend.cars.domain.CarDto;
import com.avr.avrbackend.cars.domain.CarStatus;
import com.avr.avrbackend.cars.mapper.CarMapper;
import com.avr.avrbackend.cars.repository.CarRepository;
import com.avr.avrbackend.cars.service.CarService;
import com.avr.avrbackend.user.domain.User;
import com.avr.avrbackend.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CarServiceTestSuite {

    @Mock
    private CarRepository carRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CarService carService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllCars() {
        Car car = new Car();
        List<Car> cars = Arrays.asList(car);

        when(carRepository.findAll()).thenReturn(cars);

        List<Car> result = carService.getAllCars();

        assertEquals(cars, result);
        verify(carRepository, times(1)).findAll();
    }

    @Test
    void testGetCarById() {
        Long carId = 1L;
        Car car = new Car();

        when(carRepository.findById(carId)).thenReturn(Optional.of(car));

        Optional<Car> result = carService.getCarById(carId);

        assertTrue(result.isPresent());
        assertEquals(car, result.get());
        verify(carRepository, times(1)).findById(carId);
    }

    @Test
    void testCreateCar() {
        Car car = new Car();

        when(carRepository.save(car)).thenReturn(car);

        Car result = carService.createCar(car);

        assertEquals(car, result);
        verify(carRepository, times(1)).save(car);
    }

    @Test
    void testUpdateCar() {
        Car car = new Car();

        when(carRepository.save(car)).thenReturn(car);

        Car result = carService.updateCar(car);

        assertEquals(car, result);
        verify(carRepository, times(1)).save(car);
    }

    @Test
    void testDeleteCar() {
        Long carId = 1L;

        carService.deleteCar(carId);

        verify(carRepository, times(1)).deleteById(carId);
    }

    @Test
    void testLockCar() {
        Long carId = 1L;
        Car car = new Car();

        when(carRepository.findById(carId)).thenReturn(Optional.of(car));

        assertDoesNotThrow(() -> carService.lockCar(carId));
        assertEquals(CarStatus.LOCKED, car.getCarStatus());
        verify(carRepository, times(1)).findById(carId);
        verify(carRepository, times(1)).save(car);
    }

    @Test
    void testLockCarNotFound() {
        Long carId = 1L;

        when(carRepository.findById(carId)).thenReturn(Optional.empty());

        assertThrows(CarNotFoundExeption.class, () -> carService.lockCar(carId));
        verify(carRepository, times(1)).findById(carId);
        verify(carRepository, never()).save(any());
    }

    @Test
    void testUnlockCar() {
        Long carId = 1L;
        Car car = new Car();

        when(carRepository.findById(carId)).thenReturn(Optional.of(car));

        assertDoesNotThrow(() -> carService.unlockCar(carId));
        assertEquals(CarStatus.ACTIVE, car.getCarStatus());
        verify(carRepository, times(1)).findById(carId);
        verify(carRepository, times(1)).save(car);
    }

    @Test
    void testUnlockCarNotFound() {
        Long carId = 1L;

        when(carRepository.findById(carId)).thenReturn(Optional.empty());

        assertThrows(CarNotFoundExeption.class, () -> carService.unlockCar(carId));
        verify(carRepository, times(1)).findById(carId);
        verify(carRepository, never()).save(any());
    }

    @Test
    void testAssignUserToCar() {
        Long carId = 1L;
        Long userId = 1L;
        Car car = new Car();
        User user = new User();

        when(carRepository.findById(carId)).thenReturn(Optional.of(car));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(carRepository.save(car)).thenReturn(car);

        Car result = carService.assignUserToCar(carId, userId);

        assertEquals(user, result.getUser());
        verify(carRepository, times(1)).findById(carId);
        verify(userRepository, times(1)).findById(userId);
        verify(carRepository, times(1)).save(car);
    }

    @Test
    void testAssignUserToCarCarNotFound() {
        Long carId = 1L;
        Long userId = 1L;

        when(carRepository.findById(carId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> carService.assignUserToCar(carId, userId));
        verify(carRepository, times(1)).findById(carId);
        verify(userRepository, never()).findById(any());
        verify(carRepository, never()).save(any());
    }

    @Test
    void testAssignUserToCarUserNotFound() {
        Long carId = 1L;
        Long userId = 1L;
        Car car = new Car();

        when(carRepository.findById(carId)).thenReturn(Optional.of(car));
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> carService.assignUserToCar(carId, userId));
        verify(carRepository, times(1)).findById(carId);
        verify(userRepository, times(1)).findById(userId);
        verify(carRepository, never()).save(any());
    }

    private final CarMapper carMapper = new CarMapper();
    @Test
    void testMapToCar() {
        CarDto carDto = new CarDto();
        carDto.setBrand("Toyota");
        carDto.setModel("Camry");
        carDto.setCarYear(2020);
        carDto.setRegistrationNumber("ABC123");

        Car car = carMapper.mapToCar(carDto);

        assertEquals("Toyota", car.getBrand());
        assertEquals("Camry", car.getModel());
        assertEquals(2020, car.getCarYear());
        assertEquals("ABC123", car.getRegistrationNumber());
        assertEquals(CarStatus.ACTIVE, car.getCarStatus());
    }

    @Test
    void testMapToCarDto() {
        Car car = Car.builder()
                .carId(1L)
                .brand("Toyota")
                .model("Camry")
                .carYear(2020)
                .registrationNumber("ABC123")
                .carStatus(CarStatus.ACTIVE)
                .build();

        CarDto carDto = carMapper.mapToCarDto(car);

        assertEquals(1L, carDto.getCarId());
        assertEquals("Toyota", carDto.getBrand());
        assertEquals("Camry", carDto.getModel());
        assertEquals(2020, carDto.getCarYear());
        assertEquals("ABC123", carDto.getRegistrationNumber());
        assertEquals(CarStatus.ACTIVE, carDto.getCarStatus());
    }
}
