package com.avr.avrbackend.order.mapper;

import com.avr.avrbackend.cars.domain.Car;
import com.avr.avrbackend.cars.domain.CarDto;
import com.avr.avrbackend.cars.domain.CarStatus;
import com.avr.avrbackend.order.domain.Order;
import com.avr.avrbackend.order.domain.OrderDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    public Order mapToOrder (OrderDto orderDto){
        return Order.builder()
                .startDate(orderDto.getStartDate())
                .endDate(orderDto.getEndDate())
                .price(orderDto.getPrice())
                .build();
    }

    public OrderDto mapToOrderDto (Order order){
        List<CarDto> carDtos = order.getCars().stream()
                .map(car -> new CarDto(
                        car.getCarId(),
                        car.getBrand(),
                        car.getModel(),
                        car.getCarYear(),
                        car.getRegistrationNumber(),
                        car.getCarStatus()))
                .collect(Collectors.toList());

        return new OrderDto(
                order.getOrderId(),
                order.getStartDate(),
                order.getEndDate(),
                order.getPrice(),
                carDtos
        );
    }
}
