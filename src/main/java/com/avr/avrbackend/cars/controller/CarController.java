package com.avr.avrbackend.cars.controller;

import com.avr.avrbackend.cars.domain.Car;
import com.avr.avrbackend.cars.domain.CarDto;
import com.avr.avrbackend.cars.mapper.CarMapper;
import com.avr.avrbackend.cars.repository.CarRepository;
import com.avr.avrbackend.cars.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cars")
public class CarController {

    private final CarMapper carMapper;

    private final CarService carService;

    @GetMapping
    public ResponseEntity<List<CarDto>> getListOfCars(){
        List<Car> carList = carService.getAllCars();
        List<CarDto> dtos = carList.stream()
                .map(carMapper::mapToCarDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping(value = "{catId}")
    public ResponseEntity<CarDto> getCat(@PathVariable Long catId){
        Car car = carService.getCarById(catId)
                .orElseThrow(()-> new RuntimeException("Car not found by id: " + catId));
        CarDto dto = carMapper.mapToCarDto(car);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<Void> createCar(@RequestBody CarDto carDto){
        Car car = carMapper.mapToCar(carDto);
        carService.createCar(car);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "{carId}")
    public ResponseEntity<CarDto> updateCar(@PathVariable Long carId, @RequestBody CarDto carDto){
        Car existingCar = carService.getCarById(carId)
                .orElseThrow(()->new RuntimeException("Car not found by id: " + carId));

        Car updatedCar = carMapper.mapToCar(carDto);
        updatedCar.setCarId(existingCar.getCarId());

        Car savedCar = carService.updateCar(updatedCar);
        CarDto savedDto = carMapper.mapToCarDto(savedCar);
        return ResponseEntity.ok(savedDto);
    }

    @DeleteMapping(value = "{carId}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long carId){
        carService.deleteCar(carId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/{carId}/lock")
    public ResponseEntity<Void> lockCar(@PathVariable Long carId){
        try {
            carService.lockCar(carId);
            return ResponseEntity.ok().build();
        } catch (CarNotFoundExeption e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = "/{carId}/unlock")
    public ResponseEntity<Void> unlockCar(@PathVariable Long carId){
        try {
            carService.unlockCar(carId);
            return ResponseEntity.ok().build();
        } catch (CarNotFoundExeption e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{carId}/assignUser/{userId}")
    public Car assignUserToCar(@PathVariable Long carId, @PathVariable Long userId){
        return carService.assignUserToCar(carId, userId);
    }

}
