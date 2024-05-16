package com.avr.avrbackend.cars.service;

import com.avr.avrbackend.cars.controller.CarNotFoundExeption;
import com.avr.avrbackend.cars.domain.Car;
import com.avr.avrbackend.cars.domain.CarStatus;
import com.avr.avrbackend.cars.repository.CarRepository;
import com.avr.avrbackend.user.domain.User;
import com.avr.avrbackend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CarService {

    private final CarRepository carRepository;

    private final UserRepository userRepository;

    public List<Car> getAllCars(){
        return carRepository.findAll();
    }

    public Optional<Car> getCarById(Long id){
        return carRepository.findById(id);
    }

    public Car createCar(Car car){
        return carRepository.save(car);
    }

    public Car updateCar(Car car){
        return carRepository.save(car);
    }

    public void  deleteCar(Long id){
        carRepository.deleteById(id);
    }

    public void lockCar(Long id) throws CarNotFoundExeption {
        Car existingCar = carRepository.findById(id)
                .orElseThrow(CarNotFoundExeption::new);
        existingCar.setCarStatus(CarStatus.LOCKED);
        carRepository.save(existingCar);
    }

    public void unlockCar(Long id) throws CarNotFoundExeption {
        Car existingCar = carRepository.findById(id)
                .orElseThrow(CarNotFoundExeption::new);
        existingCar.setCarStatus(CarStatus.ACTIVE);
        carRepository.save(existingCar);
    }

    public Car assignUserToCar(Long carId, Long userId){
        Car car = carRepository.findById(carId).orElseThrow(()-> new RuntimeException("Car not found by id: " + carId));
        User user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("User not found by Id: " + userId));

        car.setUser(user);
        return carRepository.save(car);
    }

}
