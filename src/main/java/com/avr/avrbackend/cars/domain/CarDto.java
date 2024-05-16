package com.avr.avrbackend.cars.domain;

import com.avr.avrbackend.user.domain.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CarDto {

        private Long carId;

        private String brand;

        private String model;

        private int carYear;

        private String registrationNumber;

        private CarStatus carStatus;
}
