package com.avr.avrbackend.cars.mapper;

import com.avr.avrbackend.cars.domain.Car;
import com.avr.avrbackend.cars.domain.CarDto;
import com.avr.avrbackend.cars.domain.CarStatus;
import org.springframework.stereotype.Component;

@Component
public class CarMapper {

    public Car mapToCar (CarDto carDto){
        return Car.builder()
                .brand(carDto.getBrand())
                .model(carDto.getModel())
                .carYear(carDto.getCarYear())
                .registrationNumber(carDto.getRegistrationNumber())
                .carStatus(CarStatus.ACTIVE)
                .build();
    }

    public CarDto mapToCarDto(Car car) {
        CarDto carDto = new CarDto();
        carDto.setCarId(car.getCarId());
        carDto.setBrand(car.getBrand());
        carDto.setModel(car.getModel());
        carDto.setCarYear(car.getCarYear());
        carDto.setRegistrationNumber(car.getRegistrationNumber());
        carDto.setCarStatus(car.getCarStatus());
        return carDto;
    }
}
