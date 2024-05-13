package com.avr.avrbackend.cars.mapper;

import com.avr.avrbackend.cars.domain.Car;
import com.avr.avrbackend.cars.domain.CarDto;
import org.springframework.stereotype.Component;

@Component
public class CarMapper {

    public Car mapToCar (CarDto carDto){
        return Car.builder()
                .brand(carDto.getBrand())
                .model(carDto.getModel())
                .year(carDto.getYear())
                .registrationNumber(carDto.getRegistrationNumber())
                .build();
    }

    public CarDto mapToCarDto (Car car){
        return new CarDto(
                car.getCarId(),
                car.getBrand(),
                car.getModel(),
                car.getYear(),
                car.getRegistrationNumber()
        );
    }
}
