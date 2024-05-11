package com.avr.avrbackend.cars.controller;

import com.avr.avrbackend.cars.domain.CarDto;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class CarController {

    private final List<CarDto> cars = new ArrayList<>();

    @GetMapping
    public List<CarDto> getAllCars() {
        return cars;
    }

    @GetMapping("/{carId}")
    public CarDto getCarById(@PathVariable Long carId) {
        for (CarDto car : cars) {
            if (car.getCarId().equals(carId)) {
                return car;
            }
        }
        throw new RuntimeException("Car not found for id: " + carId);
    }

    @PostMapping
    public void addCar(@RequestBody CarDto carDto) {
        cars.add(carDto);
    }

    @PutMapping("/{carId}")
    public void updateCar(@PathVariable Long carId, @RequestBody CarDto carDto) {
        for (int i = 0; i < cars.size(); i++) {
            if (cars.get(i).getCarId().equals(carId)) {
                cars.set(i, carDto);
                return;
            }
        }
        throw new RuntimeException("Car not found for id: " + carId);
    }

    @DeleteMapping("/{carId}")
    public void deleteCar(@PathVariable Long carId) {
        cars.removeIf(car -> car.getCarId().equals(carId));
    }
}
