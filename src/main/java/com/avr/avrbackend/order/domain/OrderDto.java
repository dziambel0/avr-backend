package com.avr.avrbackend.order.domain;


import com.avr.avrbackend.cars.domain.CarDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class OrderDto {

    private Long orderId;

    private LocalDate startDate;

    private LocalDate endDate;

    private double price;

    List<CarDto> cars;
}
